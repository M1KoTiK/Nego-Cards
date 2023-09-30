package m1k.kotik.negocards.data.serialization.value_converters

import java.lang.reflect.Type

interface IValueSerializationSet {
    val map: Map<Type, IValueSerialization<Type>>
}