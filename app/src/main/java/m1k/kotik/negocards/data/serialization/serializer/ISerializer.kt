package m1k.kotik.negocards.data.serialization.serializer

import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.ISerializationParser
import m1k.kotik.negocards.data.serialization.value_converters.IValueSerializationSet

interface ISerializer<T> where T: ISerializationObject {
    var parser: ISerializationParser
    fun serialize(valueSerializationSet: IValueSerializationSet): String
    fun deserialize(code: String, serializationObject: T): T
}