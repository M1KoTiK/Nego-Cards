package m1k.kotik.negocards.data.serialization.serializer

import m1k.kotik.negocards.data.serialization.parser.ISerializationParser
import m1k.kotik.negocards.data.serialization.TestParser
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject

abstract class TestSerializer<T : ISerializationObject>() : ISerializer<T> {
    override var parser: ISerializationParser = TestParser()
    override fun deserialize(code: String, serializationObject: T): T {
        TODO("Not yet implemented")
    }
}