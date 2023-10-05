package m1k.kotik.negocards.data.serialization.value_converters

class SimpleValueConverter<T>(): IValueConverter<T> {
    override val valueStarts: String = "("
    override val valueEnds: String= ")"

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(serializationValue: String): T {
        val outputValue: T
        val value = serializationValue.drop(valueStarts.length).dropLast(valueEnds.length)
            outputValue = value as T
        return outputValue
    }

    override fun serialize(value: T): String {
        return  "${valueStarts}${value}${value}"
    }

}