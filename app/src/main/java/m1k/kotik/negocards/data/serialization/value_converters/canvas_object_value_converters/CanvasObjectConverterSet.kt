package m1k.kotik.negocards.data.serialization.value_converters.canvas_object_value_converters

import m1k.kotik.negocards.data.serialization.value_converters.IValueConverter
import m1k.kotik.negocards.data.serialization.value_converters.IValueConverterSet
import java.lang.reflect.Type

class CanvasObjectConverterSet: IValueConverterSet{
    override val objectSeparator: String = ";"
    override val map: MutableMap<Type, IValueConverter<*>>
        get() = mutableMapOf(
            String::class.java to CanvasObjectStringConverter(),
            Int::class.java to CanvasObjectIntConverter(),
            Array::class.java to CanvasObjectArrayConverter<Any>()
        )
}