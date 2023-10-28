package m1k.kotik.negocards.data.serialization.serializationObject

class TestSerializeObject: ISerializationObject {
    override val key: String = "sq"
    @SerializeMember("x")
    var integer:Int  = 20

    @SerializeMember("y")
    var text:Int = 122

    @SerializeMember("u")
    var isUse: Boolean = true

    @SerializeMember("l")
    var list = listOf(1,"3")
}