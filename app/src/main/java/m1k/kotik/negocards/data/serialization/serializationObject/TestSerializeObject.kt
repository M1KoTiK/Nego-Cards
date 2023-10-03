package m1k.kotik.negocards.data.serialization.serializationObject

class TestSerializeObject: ISerializationObject {
    override val key: String = "testObject"
    @SeriаlizationMember("int")
    var integer  = 1234

    @SeriаlizationMember("text")
    var text = "1256"

    @SeriаlizationMember("list")
    var list = listOf(1,"3")
}