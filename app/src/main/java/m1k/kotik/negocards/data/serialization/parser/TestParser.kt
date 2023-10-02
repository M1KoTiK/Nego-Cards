package m1k.kotik.negocards.data.serialization

import m1k.kotik.negocards.data.serialization.parser.ISerializationParser
import m1k.kotik.negocards.data.serialization.parser.TypedValue
import m1k.kotik.negocards.data.serialization.reflection.getMemberKeys
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.value_converters.IValueConverterSet
import m1k.kotik.negocards.data.serialization.value_converters.canvas_object_value_converters.CanvasObjectConverterSet
import java.lang.reflect.Type

class TestParser: ISerializationParser {
    override val converterSet: IValueConverterSet = CanvasObjectConverterSet()

    private val listAllStartAllocator = getAllStartAllocators()
    private val mapAllocations = getAllAllocations()
    private val listRequiredType = getAllConvertableType()

    // не вернет ключ для типа самого сериализуемого объекта - он обрабатывается в ISerializer
    override fun parseString(
        serializationString: String,
        sObj: ISerializationObject
    ): Map<String, TypedValue> {
        val objectMemberKeys = getMemberKeys(sObj)
        val outputMap = mutableMapOf<String, TypedValue>()
        var index = 0

        fun searchKey(): String?{
            var scanValue: String = ""
            while(index< serializationString.count()){
                scanValue += serializationString[index]
                if(getMemberKeys(sObj).contains(scanValue)){
                    index++
                    return scanValue;
                }
                index++
            }
            return null
        }

        fun goToEndAllocator(startAllocator: String): String?{
            val endAllocator = mapAllocations[startAllocator] ?: return null
            var subSerializationString = serializationString.substring(index,serializationString.count())
            var indexEndAllocator = subSerializationString.indexOf(endAllocator)
            if(indexEndAllocator == -1){
                return null
            }
            var value = subSerializationString.substring(0,indexEndAllocator)
                index += indexEndAllocator
            return value

        }

        fun searchValue(): TypedValue?{
            var scanValue: String = ""
            var startAllocatorValue = ""
            var type: Type? = null
            var value: Any
            while(index< serializationString.count()) {
                scanValue += serializationString[index]
                if (listAllStartAllocator.contains(scanValue)) {
                    startAllocatorValue = scanValue
                    scanValue = ""
                    index++
                    value = goToEndAllocator(startAllocatorValue) ?: return null
                    for (t in listRequiredType) {
                        if (converterSet.map[t]!!.valueStarts == startAllocatorValue) {
                            type = t
                            break
                        }
                    }
                    if(type != null) {
                        index++
                        return TypedValue(type, value)
                    }
                }
                index++
            }
            return null
        }

        //работа функции
        while(index < serializationString.count()){
            val key = searchKey()
            val typedValue = searchValue()
            if(key != null && typedValue != null){
                outputMap[key] = typedValue
            }
        }
        return outputMap
    }



    override fun parseObject(sObj: ISerializationObject): Map<String, TypedValue> {
        TODO("Not yet implemented")
    }
    private fun getAllConvertableType(): MutableList<Type>{
        val outputList = mutableListOf<Type>()
        for(type in converterSet.map.keys){
            outputList.add(type)
        }
        return outputList
    }
    private fun getAllStartAllocators():List<String>{
        val outputList = mutableListOf<String>()
        for(value in converterSet.map.values){
            outputList.add(value.valueStarts)
        }
        return outputList
    }
    private fun getAllAllocations():Map<String, String>{
        val outputList = mutableMapOf<String, String>()
        for(value in converterSet.map.values){
            outputList[value.valueStarts] = value.valueEnds
        }
        return outputList
    }
}