package m1k.kotik.negocards.data.serialization.value_serializations

class CanvasObjectStringSerialization: IValueSerialization<String> {
    override fun serialize(value: String): String {
        return "(${value})"
    }

    override fun deserialize(serializationValue: String): String {
        return serializationValue.drop(1).dropLast(1)
    }
}

class CanvasObjectIntSerialization: IValueSerialization<Int>{
    override fun serialize(value: Int): String {
        return value.toString()
    }

    override fun deserialize(serializationValue: String): Int {
        return serializationValue.toInt()
    }
class CanvasObjectListSerialization
}