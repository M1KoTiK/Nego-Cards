package m1k.kotik.negocards.data.serialization.parser

import m1k.kotik.negocards.data.serialization.reflection.getMemberKeysAndTypedValue
import m1k.kotik.negocards.data.serialization.reflection.getMemberKeysAndTypes
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.string_utils.findRestrictedBetween
import m1k.kotik.negocards.data.serialization.value_converters.IValueConverterSet
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.createInstance

class DefaultParser(
    override var converterSet: IValueConverterSet,
    override val requiredObjectMap: Map<String, KClass<*>>,
    override var checkTypeChange: Boolean = true,
    var maxLengthForFindObjectSeparator: Int = 5,
    var maxLengthForFindObjectKey: Int = 5
): ISerializationParser {

    override fun parseString(
        serializationString: String,
    ): List<SeparatedObject> {
        var memberKeyAndTypes = mutableMapOf<String,KType>()
        val outputList = mutableListOf<SeparatedObject>()
        var index = 0

        fun searchObjectKey(): String?{
            var scanValue = ""
            for(i in 0..maxLengthForFindObjectKey){
                scanValue += serializationString[index + i]
                if(requiredObjectMap.containsKey(scanValue)){
                    index += i + converterSet.splitSign.length + 1
                    return scanValue
                }
            }
            return null
        }
        fun searchValueKey(): String?{
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
                    index++
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
        fun checkEnd():Boolean{
            var scanValue = ""
            for(i in 0..maxLengthForFindObjectSeparator){
                if(index + i < serializationString.count()) {
                    scanValue += serializationString[index + i].toString()
                    if(scanValue == converterSet.objectSeparator){
                        index += i + 1
                        return true
                    }
                }
            }
            return false
        }

        //работа функции
        fun scanObject():Map<String, TypedValue>{
            val sObjMap: MutableMap<String, TypedValue> = mutableMapOf()
            while(index < serializationString.count()) {
                val key = searchValueKey() ?: break
                val typedValue = searchValue(key)
                if (checkEnd()) {
                    if (typedValue != null) {
                        sObjMap[key] = typedValue
                    }
                    return sObjMap
                } else {
                    if (typedValue != null) {
                        sObjMap[key] = typedValue
                    }
                }
            }
            return sObjMap
        }
        while(index < serializationString.count()) {
            val objKey = searchObjectKey()?: break
            val kClass = requiredObjectMap[objKey] ?: break
            val instance = kClass.createInstance() as ISerializationObject
            memberKeyAndTypes = getMemberKeysAndTypes(instance)
            val sepObj = SeparatedObject(objKey,scanObject())
            outputList.add(sepObj)
        }
        return outputList
    }

    override fun parseObject(listSObj: List<ISerializationObject>): List<SeparatedObject> {
        val outputList =  mutableListOf<SeparatedObject>()
        for(sObj in listSObj){
            val key = sObj.key
            val sepObj = SeparatedObject(key, getMemberKeysAndTypedValue(sObj))
            outputList.add(sepObj)
        }
        return outputList
    }
    //------------------------------------
    //Списки с вспомогательными значениями
    //----------------------------------------
    private val listAllStartAllocator = getAllStartAllocators(converterSet)
    private val mapAllocations = getAllAllocations(converterSet)
    private val listRequiredType = getAllConvertableType(converterSet)

    companion object {

        fun getAllConvertableType(converterSet: IValueConverterSet): MutableList<KType>{
            val outputList = mutableListOf<KType>()
            for(type in converterSet.typeToConverterMap.keys){
                outputList.add(type)
            }
            return outputList
        }

        fun getAllAllocations(converterSet: IValueConverterSet):Map<String, String>{
            val outputList = mutableMapOf<String, String>()
            for(value in converterSet.typeToConverterMap.values){
                outputList[value.valueStarts] = value.valueEnds
            }
            return outputList
        }

        fun getAllStartAllocators(converterSet: IValueConverterSet):List<String>{
            val outputList = mutableListOf<String>()
            for(value in converterSet.typeToConverterMap.values){
                outputList.add(value.valueStarts)
            }
            return outputList
        }
    }
}