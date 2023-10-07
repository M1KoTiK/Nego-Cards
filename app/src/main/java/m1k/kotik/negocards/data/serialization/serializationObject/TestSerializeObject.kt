package m1k.kotik.negocards.data.serialization.serializationObject

class TestSerializeObject: ISerializationObject {
    override val key: String = "sq"
    @SeriаlizationMember("x")
    var integer:Int  = 20

    @SeriаlizationMember("y")
    var text:Int = 122

    @SeriаlizationMember("u")
    var isUse: Boolean = true

    @SeriаlizationMember("l")
    var list = listOf(1,"3")
}