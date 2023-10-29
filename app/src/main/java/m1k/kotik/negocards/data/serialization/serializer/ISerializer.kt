package m1k.kotik.negocards.data.serialization.serializer

import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.parser.ISerializationParser
import kotlin.reflect.KClass

interface ISerializer{
    var parser: ISerializationParser
    fun serialize(listSerializeObject: List<ISerializationObject>): String?
    fun <T> deserialize(code: String): List<T>?
}