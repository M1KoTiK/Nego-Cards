package m1k.kotik.negocards.data.serialization.serializer

import m1k.kotik.negocards.data.serialization.parser.ISerializationParser
import m1k.kotik.negocards.data.serialization.reflection.writeOnKey
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import kotlin.reflect.full.createInstance

/**
 * @param[parser] задает используемый парсер (ISerializationParser)
 * @param[requiredObjectMap] задает список типов сериализуемых объектов
 * @param[lengthForKey] задает максимальную длину для ключа объекта
 * @constructor Предназначен для сериализации и десериализации одного объекта.
 */
open class DefaultSerializer(
    override var parser: ISerializationParser,
    private var lengthForKey: Int = 10
): ISerializer {

    override fun serialize(listSerializeObject: List<ISerializationObject>): String? {
    var outputString:String = ""
        val listSepObject = parser.parseObject(listSerializeObject)
        for(sepObj in listSepObject){
            outputString += sepObj.objKey
            //Если есть символ для отделения ключа то добавляем его
            if (parser.converterSet.splitSign.isNotEmpty()) {
                outputString += parser.converterSet.splitSign
            }
            val valuesMap = sepObj.objectValues ?: return null
            for(memberKey in valuesMap.keys){
                val typedValue = valuesMap[memberKey] ?: continue
                val type = typedValue.type
                val value = typedValue.value
                val converter = parser.converterSet.typeToConverterMap[type] ?: continue
                outputString += memberKey
                outputString += converter.serialize(value)
            }
            outputString += parser.converterSet.objectSeparator
        }

    return outputString
    }


    override fun <T> deserialize(code: String): List<T>? {
        // Пропускаем символы ключа и символ для того чтобы его отделить от значений объекта
        val outputList = mutableListOf<T>()
        val listDesObject = parser.parseString(code)
        for(sepObj in listDesObject) {
            val valuesMap = sepObj.objectValues?: return null
            val kClass = parser.requiredObjectMap[sepObj.objKey] ?: return null
            val instance = kClass.createInstance() as ISerializationObject
            for (memberKey in valuesMap.keys) {
                val typedValue = valuesMap[memberKey] ?: return null
                val memberValueType = typedValue.type
                val memberValue = typedValue.value
                writeOnKey(memberKey, memberValue, instance)
            }
            @Suppress("UNCHECKED_CAST")
            outputList.add(instance as T)
        }
        return outputList
    }
}