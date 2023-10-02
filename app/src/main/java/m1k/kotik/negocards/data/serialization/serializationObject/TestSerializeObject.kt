package m1k.kotik.negocards.data.serialization.serializationObject

class TestSerializeObject: ISerializationObject {
    override val key: String = "testObject"
    @SeriаlizationMember("int")
    var integer  = 123

    @SeriаlizationMember("text")
    var text = "1256"
}