package m1k.kotik.negocards.data.serialization.serializer

import m1k.kotik.negocards.data.serialization.parser.ISerializationParser
import m1k.kotik.negocards.data.serialization.DefaultParser
import m1k.kotik.negocards.data.serialization.reflection.getMemberAnnotation
import m1k.kotik.negocards.data.serialization.reflection.writeOnKey
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.serializationObject.TestSerializeObject
import m1k.kotik.negocards.data.serialization.value_converters.IValueConverterSet
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class TestSerializer(var lengthForKey: Int = 5): ISerializer {
    override val requiredObjectList: MutableList<KType>
        get() = TODO("Not yet implemented")
    override var parser: ISerializationParser = DefaultParser()

    override fun serialize(serializeObject: ISerializationObject): String {
    TODO()
    }

    override fun <T : ISerializationObject> deserialize(code: String, serializeObject: T): T {
    TODO()
    }
}