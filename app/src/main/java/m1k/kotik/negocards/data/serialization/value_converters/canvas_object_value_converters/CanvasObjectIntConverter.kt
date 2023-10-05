package m1k.kotik.negocards.data.serialization.value_converters.canvas_object_value_converters

import m1k.kotik.negocards.data.serialization.value_converters.IValueConverter

class CanvasObjectIntConverter: IValueConverter<Int> {
    override val valueStarts: String = "("
    override val valueEnds: String = ")"
    override fun deserialize(serializationValue: String): Int {
        return serializationValue.drop(valueStarts.length).dropLast(valueEnds.length).toInt()
    }

    override fun serialize(value: Any): String {
        return "${valueStarts}${value}${valueEnds}"
    }
}