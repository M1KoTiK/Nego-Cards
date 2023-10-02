package m1k.kotik.negocards.data.serialization.value_converters

import java.lang.reflect.Type

interface IValueConverterSet {
    //разделитель объектов не может иметь в себе цифру
    val objectSeparator: String
    val map: Map<Type, IValueConverter<*>>
}