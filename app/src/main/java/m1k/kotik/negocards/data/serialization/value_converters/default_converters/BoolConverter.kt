package m1k.kotik.negocards.data.serialization.value_converters.default_converters

import m1k.kotik.negocards.data.serialization.value_converters.IValueConverter

class BoolConverter(override val valueStarts: String = "(", override val valueEnds: String = ")") : IValueConverter<Boolean> {

    override fun serialize(value: Any): String {
        return "${valueStarts}${value}${valueEnds}"
    }

    override fun deserialize(serializationValue: String): Boolean {
        return serializationValue.drop(valueStarts.length).dropLast(valueEnds.length).toBoolean()
    }

}