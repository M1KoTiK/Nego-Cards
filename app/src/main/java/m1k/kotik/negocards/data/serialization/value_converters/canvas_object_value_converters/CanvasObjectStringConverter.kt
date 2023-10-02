package m1k.kotik.negocards.data.serialization.value_converters.canvas_object_value_converters

import m1k.kotik.negocards.data.serialization.value_converters.IValueConverter

class CanvasObjectStringConverter: IValueConverter<String> {
    override val valueStarts: String = "\""
    override val valueEnds: String = "\""
    //string
    override fun serialize(value: String): String {
        return "(${value})"
    }
    override fun deserialize(serializationValue: String): String {
        return serializationValue.drop(1).dropLast(1)
    }



}