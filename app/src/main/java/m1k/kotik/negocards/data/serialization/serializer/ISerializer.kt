package m1k.kotik.negocards.data.serialization.serializer

import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.parser.ISerializationParser
import m1k.kotik.negocards.data.serialization.value_converters.IValueConverterSet

interface ISerializer<T> where T: ISerializationObject {
    var parser: ISerializationParser
    fun serialize(valueSerializationSet: IValueConverterSet): String
    fun deserialize(code: String, serializationObject: T): T
}