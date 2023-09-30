package m1k.kotik.negocards.data.serialization.serializer

import m1k.kotik.negocards.data.serialization.ISerializationObject
import m1k.kotik.negocards.data.serialization.ISerializationParser

interface ISerializer<T> where T: ISerializationObject{
    fun serialize(parser: ISerializationParser): String
    fun deserialize(code: String, serializationObject: T): T
}