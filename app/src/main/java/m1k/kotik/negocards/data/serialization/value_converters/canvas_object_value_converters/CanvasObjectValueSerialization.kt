package m1k.kotik.negocards.data.serialization.value_converters.canvas_object_value_converters

import m1k.kotik.negocards.data.serialization.value_converters.IValueSerialization

class CanvasObjectStringConverter: IValueSerialization<String> {
    //string
    override fun serialize(value: String): String {
        return "(${value})"
    }
    override fun deserialize(serializationValue: String): String {
        return serializationValue.drop(1).dropLast(1)
    }
}
    //int
class CanvasObjectIntConverter: IValueSerialization<Int> {
    override fun serialize(value: Int): String {
        return value.toString()
    }
    override fun deserialize(serializationValue: String): Int {
        return serializationValue.toInt()
    }
}
    //Array
class CanvasObjectArrayConverter<T> : IValueSerialization<Array<T>> {
        override fun serialize(value: Array<T>): String {
            val stringBuilder = StringBuilder()
            stringBuilder.append("[")
            for (arrayValue in value) {
                if (stringBuilder.count() == 1) {
                    stringBuilder.append("${arrayValue.toString()}")
                } else {
                    stringBuilder.append(",${arrayValue.toString()}")
                }
            }
            stringBuilder.append("]")
            return stringBuilder.toString()
        }

        override fun deserialize(serializationValue: String): Array<T> {
            TODO("Not yet implemented")
        }
    }