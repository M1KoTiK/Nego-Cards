package m1k.kotik.negocards.data.serialization.value_converters.canvas_object_value_converters

import m1k.kotik.negocards.data.serialization.value_converters.IValueConverter

class CanvasObjectArrayConverter<T> : IValueConverter<Array<T>> {
    override val valueStarts: String = "["
    override val valueEnds: String = "]"
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