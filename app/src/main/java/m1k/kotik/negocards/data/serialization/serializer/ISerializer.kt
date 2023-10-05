package m1k.kotik.negocards.data.serialization.serializer

import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.parser.ISerializationParser
import kotlin.reflect.KClass

interface ISerializer{
    val requiredObjectMap: Map<String, KClass<*>>
    var parser: ISerializationParser
    fun serialize(serializeObject: ISerializationObject): String?
    fun <T> deserialize(code: String): T?
}