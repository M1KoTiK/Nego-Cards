package m1k.kotik.negocards.data.serialization

import m1k.kotik.negocards.data.serialization.parser.ISerializationParser
import m1k.kotik.negocards.data.serialization.parser.TypedValue
import m1k.kotik.negocards.data.serialization.reflection.getMemberKeys
import m1k.kotik.negocards.data.serialization.reflection.getMemberKeysAndTypedValue
import m1k.kotik.negocards.data.serialization.reflection.getMemberKeysAndTypes
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.string_utils.findRestrictedBetween
import m1k.kotik.negocards.data.serialization.value_converters.IValueConverterSet
import m1k.kotik.negocards.data.serialization.value_converters.TestConverterSet
import java.lang.reflect.Type
import kotlin.reflect.KType

class DefaultParser: ISerializationParser {
    override val converterSet: IValueConverterSet = TestConverterSet() //CanvasObjectConverterSet()

    // не вернет ключ для типа самого сериализуемого объекта - он обрабатывается в ISerializer
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
                scanValue += serializationString[index]
                if(memberKeyAndTypes.containsKey(scanValue)){
                    index++
                    return scanValue;
                }
                index++
            }
            return null
        }



        fun searchValue(key: String): TypedValue?{
            var scanValue: String = ""
            var startAllocatorValue = ""
            var value: Any
            while(index< serializationString.count()) {
                scanValue += serializationString[index]
                if (listAllStartAllocator.contains(scanValue)) {
                    startAllocatorValue = scanValue
                    scanValue = ""

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
    //Списки с всопомогательными значениями
    //----------------------------------------
    private val listAllStartAllocator = Companion.getAllStartAllocators(this)
    private val mapAllocations = Companion.getAllAllocations(this)
    private val listRequiredType = Companion.getAllConvertableType(this)

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