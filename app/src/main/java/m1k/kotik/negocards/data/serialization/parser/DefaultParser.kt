package m1k.kotik.negocards.data.serialization.parser

import m1k.kotik.negocards.data.serialization.reflection.getMemberKeysAndTypedValue
import m1k.kotik.negocards.data.serialization.reflection.getMemberKeysAndTypes
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.string_utils.findRestrictedBetween
import m1k.kotik.negocards.data.serialization.value_converters.IValueConverterSet
import m1k.kotik.negocards.data.serialization.value_converters.TestConverterSet
import kotlin.reflect.KType

// не вернет ключ для типа самого сериализуемого объекта - он обрабатывается в ISerializer
class DefaultParser: ISerializationParser {
    override var checkTypeChange: Boolean = true
    // Для того чтобы парсить сразу значения нескольких объектов
    // нужно понимать на каком индексе находится парсер после парса значений одного объекта
    // так как сам парсер не знает ничего об объекте и дальнейшую логику выполняет сериализатор
    override var serializationStringSplitObjectValueIndex: Int = 0
    override var converterSet: IValueConverterSet = TestConverterSet()
    override fun parseString(
        serializationString: String,
        sObj: ISerializationObject
    ): Map<String, TypedValue> {
        val memberKeyAndTypes = getMemberKeysAndTypes(sObj)
        val outputMap = mutableMapOf<String, TypedValue>()
        var index = 0

        fun searchKey(): String?{
            var scanValue: String = ""
            while(index< serializationString.count()){
                if(scanValue == converterSet.objectSeparator){
                    serializationStringSplitObjectValueIndex = index
                    return null
                }
                scanValue += serializationString[index]
                if(memberKeyAndTypes.containsKey(scanValue)){
                    index++
                    return scanValue;
                }
                index++
            }
            return null
        }
        fun checkTypeChange(text: String):Boolean{
            if(text.toDoubleOrNull()!=null){
                return true
            }
            return false
        }

        fun searchValue(key: String): TypedValue?{
            var scanValue: String = ""
            var startAllocatorValue = ""
            var value: Any
            while(index < serializationString.count()) {
                scanValue += serializationString[index]
                if(checkTypeChange(scanValue)){
                    while(
                        index+1 < serializationString.count() &&
                        checkTypeChange(scanValue + serializationString[index + 1])
                    ){
                        index++
                        scanValue += serializationString[index]
                    }
                    var type = memberKeyAndTypes[key] ?: return null
                    var converter = converterSet.typeToConverterMap[type]
                    value = converter?.deserialize(scanValue) ?: return null
                    return TypedValue(type, value)
                }
                else if (listAllStartAllocator.contains(scanValue)) {
                    startAllocatorValue = scanValue
                    val endAllocator = mapAllocations[startAllocatorValue] ?: return null
                    var subSerializationString = serializationString.substring(index,serializationString.count())

                    var subStringForValueConvert = findRestrictedBetween(
                        subSerializationString,
                        startAllocatorValue,
                        endAllocator
                    ) ?: return null
                    var type = memberKeyAndTypes[key] ?: return null
                    var converter = converterSet.typeToConverterMap[type]
                    value = converter?.deserialize(subStringForValueConvert) ?: return null
                    index += subStringForValueConvert.length
                    return TypedValue(type, value)
                }
                index++
            }
            return null
        }

        //работа функции
        while(index < serializationString.count()){
            val key = searchKey() ?: continue
            val typedValue = searchValue(key)
            if(typedValue != null){
                outputMap[key] = typedValue
            }
        }
        return outputMap
    }

    override fun parseObject(sObj: ISerializationObject): Map<String, TypedValue> {
        return getMemberKeysAndTypedValue(sObj)
    }
    //------------------------------------
    //Списки с вспомогательными значениями
    //----------------------------------------
    private val listAllStartAllocator = getAllStartAllocators(this)
    private val mapAllocations = getAllAllocations(this)
    private val listRequiredType = getAllConvertableType(this)

    companion object {

        fun getAllConvertableType(defaultParser: DefaultParser): MutableList<KType>{
            val outputList = mutableListOf<KType>()
            for(type in defaultParser.converterSet.typeToConverterMap.keys){
                outputList.add(type)
            }
            return outputList
        }

        fun getAllAllocations(defaultParser: DefaultParser):Map<String, String>{
            val outputList = mutableMapOf<String, String>()
            for(value in defaultParser.converterSet.typeToConverterMap.values){
                outputList[value.valueStarts] = value.valueEnds
            }
            return outputList
        }

        fun getAllStartAllocators(defaultParser: DefaultParser):List<String>{
            val outputList = mutableListOf<String>()
            for(value in defaultParser.converterSet.typeToConverterMap.values){
                outputList.add(value.valueStarts)
            }
            return outputList
        }
    }
}