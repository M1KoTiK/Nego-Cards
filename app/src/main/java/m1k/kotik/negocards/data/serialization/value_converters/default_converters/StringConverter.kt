package m1k.kotik.negocards.data.serialization.value_converters.default_converters

import m1k.kotik.negocards.data.serialization.value_converters.IValueConverter
import m1k.kotik.negocards.data.serialization.value_converters.IValueConverterSet

class StringConverter(override val valueStarts: String = "(", override val valueEnds: String = ")") : IValueConverter<String> {
    override fun serialize(value: Any): String {
        return "${valueStarts}${value}${valueEnds}"
    }

    override fun deserialize(serializationValue: String): String {
        return serializationValue.drop(valueStarts.length).dropLast(valueEnds.length) as String
    }

}