package m1k.kotik.negocards.data.serialization.serializer

import m1k.kotik.negocards.data.serialization.parser.ISerializationParser
import m1k.kotik.negocards.data.serialization.parser.DefaultParser
import m1k.kotik.negocards.data.serialization.reflection.writeOnKey
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.createType

open class DefaultSerializer(
    override var parser: ISerializationParser,
    override val requiredObjectMap: Map<String, KClass<*>>,
    var lengthForKey: Int = 10
): ISerializer {
    override fun getSerializeObjectKey(code: String): String? {
        return searchKey(code)
    }


    override fun serialize(serializeObject: ISerializationObject): String {
        var key = serializeObject.key

        //Если есть символ для отделения ключа то добавляем его
        if(parser.converterSet.splitSign.isNotEmpty()){
            key += parser.converterSet.splitSign
        }
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

        // Пропускаем символы ключа и символ для того чтобы его отделить от значений объекта
        val map = parser.parseString(code.drop(key.length + parser.converterSet.splitSign.length),instance)
        for (memberKey in map.keys){
            val typedValue = map[memberKey]?: return null
            val memberValueType = typedValue.type
            val memberValue = typedValue.value
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