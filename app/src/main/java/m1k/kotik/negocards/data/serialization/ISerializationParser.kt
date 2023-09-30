package m1k.kotik.negocards.data.serialization

interface ISerializationParser {
    fun parseString(serializationString: String):Map<String, Any>
}