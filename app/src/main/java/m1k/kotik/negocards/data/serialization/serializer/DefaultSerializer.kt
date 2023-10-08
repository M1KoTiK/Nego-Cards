package m1k.kotik.negocards.data.serialization.serializer

import m1k.kotik.negocards.data.serialization.parser.ISerializationParser
import m1k.kotik.negocards.data.serialization.DefaultParser
import m1k.kotik.negocards.data.serialization.reflection.writeOnKey
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.serializationObject.TestSerializeObject
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.createType

class DefaultSerializer(
    override val requiredObjectMap: Map<String, KClass<*>>,
    var lengthForKey: Int = 10
): ISerializer {

    override var parser: ISerializationParser = DefaultParser()

    override fun serialize(serializeObject: ISerializationObject): String {
        val key = serializeObject.key
        var outputString = key
        val map = parser.parseObject(serializeObject)
        for(key in map.keys){
            val typedValue = map[key] ?: continue
            val type = typedValue.type
            val value = typedValue.value
            val converter = parser.converterSet.typeToConverterMap[type] ?: continue
            outputString += key
            outputString += converter.serialize(value)

        }
        return outputString
    }

    override fun <T> deserialize(code: String): T? {
        val key = searchKey(code) ?: return null
        val kClass = requiredObjectMap[key] ?: return null
        val kType = kClass.createType()
        val instance = kClass.createInstance() as ISerializationObject
        val map = parser.parseString(code.drop(key.length),instance)
        for (memberKey in map.keys){
            val typedValue = map[memberKey]?: return null
            val memberValueType = typedValue.type
            val memberValue = typedValue.value
            //println("key = ${memberKey}, value = ${memberValue}, type = ${memberValue::class}")
           writeOnKey(memberKey,memberValue,instance)

        }
        @Suppress("UNCHECKED_CAST")
        return instance as T
    }

    private fun searchKey(code: String): String?{
        var scanValue = ""
        for(i in 0..lengthForKey){
            scanValue += code[i]
            if(requiredObjectMap.containsKey(scanValue)){
                return scanValue
            }
        }
        return null
    }


}