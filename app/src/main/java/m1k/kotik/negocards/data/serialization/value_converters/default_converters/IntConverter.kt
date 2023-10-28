package m1k.kotik.negocards.data.serialization.value_converters.default_converters

import m1k.kotik.negocards.data.serialization.value_converters.IValueConverter


class IntConverter(override val valueStarts: String = "", override val valueEnds: String = "") : IValueConverter<Int> {
    override fun serialize(value: Any): String {
        return "${valueStarts}${value}${valueEnds}"
    }

    override fun deserialize(serializationValue: String): Int {
        return serializationValue.drop(valueStarts.length).dropLast(valueEnds.length).toInt()
    }

}