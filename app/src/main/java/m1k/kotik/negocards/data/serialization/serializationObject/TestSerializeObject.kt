package m1k.kotik.negocards.data.serialization.serializationObject

import m1k.kotik.negocards.data.serialization.SeriаlizeMember

class TestSerializeObject: ISerializationObject {
    override val key: String = "testObject"
    @SeriаlizeMember("int")
    var integer  = 123

    @SeriаlizeMember("text")
    var text = "1256"
}