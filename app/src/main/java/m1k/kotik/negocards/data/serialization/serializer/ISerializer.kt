package m1k.kotik.negocards.data.serialization.serializer

import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.parser.ISerializationParser
import m1k.kotik.negocards.data.serialization.parser.TypedValue
import m1k.kotik.negocards.data.serialization.serializationObject.TestSerializeObject
import m1k.kotik.negocards.data.serialization.value_converters.IValueConverterSet
import kotlin.reflect.KType

interface ISerializer{
    val requiredObjectList: MutableList<KType>
    var parser: ISerializationParser
    fun serialize(serializeObject: ISerializationObject): String
    fun <T: ISerializationObject> deserialize(code: String, serializeObject: T): T
}