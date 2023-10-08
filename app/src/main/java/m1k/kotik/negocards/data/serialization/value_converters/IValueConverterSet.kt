package m1k.kotik.negocards.data.serialization.value_converters

import kotlin.reflect.KType

interface IValueConverterSet {
    //разделитель объектов не может иметь в себе цифру
    var splitSign: String
    var objectSeparator: String
    val typeToConverterMap: MutableMap< KType, IValueConverter<*>>
}