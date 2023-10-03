package m1k.kotik.negocards.data.serialization.value_converters

class SimpleValueConverter<T>(override val valueStarts: String,
                                       override val valueEnds: String): IValueConverter<T> {
    override fun serialize(value: T): String {
        return "${valueStarts}${value}${valueEnds}"
    }

    override fun deserialize(serializationValue: String): T {
        val stringOutputValue = serializationValue
            .drop(valueStarts.length)
            .dropLast(valueEnds.length)
        try {
            @Suppress("UNCHECKED_CAST")
            return serializationValue.drop(valueStarts.length).dropLast(valueEnds.length) as T
        }
        catch (e:Exception){
            throw ClassCastException("Ты долбаеб и значение не может быть приведено к этому типу")
        }
    }

}