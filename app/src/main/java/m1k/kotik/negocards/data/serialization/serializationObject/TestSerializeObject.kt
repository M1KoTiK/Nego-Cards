package m1k.kotik.negocards.data.serialization.serializationObject

class TestSerializeObject: ISerializationObject {
    override val key: String = "sq"
    @SeriаlizationMember("int")
    var integer:Int  = 1214

    @SeriаlizationMember("text")
    var text:String = "1256"

    @SeriаlizationMember("list")
    var list = listOf(1,"3")
}