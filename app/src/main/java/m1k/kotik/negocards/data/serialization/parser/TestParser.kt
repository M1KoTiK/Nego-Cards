package m1k.kotik.negocards.data.serialization

import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.STR_CONTAINS_CHAR
import m1k.kotik.negocards.data.serialization.parser.ISerializationParser
import m1k.kotik.negocards.data.serialization.parser.TypedValue
import m1k.kotik.negocards.data.serialization.reflection.getMemberAnnotation
import m1k.kotik.negocards.data.serialization.reflection.getMemberKeys
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.value_converters.IValueConverterSet
import m1k.kotik.negocards.data.serialization.value_converters.canvas_object_value_converters.CanvasObjectConverterSet
import java.lang.reflect.Type

class TestParser: ISerializationParser {

    private val listRequiredType = getAllConvertableType()
    private val listAllStartAllocator = getAllStartAllocators()

    override val converterSet: IValueConverterSet = CanvasObjectConverterSet()
    override fun parseString(
        serializationString: String,
        sObj: ISerializationObject
    ): Map<String, TypedValue> {
        val objectMemberKeys = getMemberKeys(sObj)
        val outputMap = mutableMapOf<String, TypedValue>()

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