package m1k.kotik.negocards.data.serialization.value_converters

import java.lang.reflect.Type
import kotlin.reflect.KType

interface IValueConverterSet {
    //разделитель объектов не может иметь в себе цифру
    val objectSeparator: String
    val typeToConverterMap: Map<KType, IValueConverter<*>>
}