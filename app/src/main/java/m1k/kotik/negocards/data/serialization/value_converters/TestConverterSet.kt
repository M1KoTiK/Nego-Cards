package m1k.kotik.negocards.data.serialization.value_converters

import m1k.kotik.negocards.data.serialization.value_converters.canvas_object_value_converters.CanvasObjectIntConverter
import m1k.kotik.negocards.data.serialization.value_converters.canvas_object_value_converters.CanvasObjectStringConverter
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf


class TestConverterSet: IValueConverterSet {

    override val objectSeparator: String
        get() = ";"
    override val typeToConverterMap: Map<KType, IValueConverter<*>>
        get() = mutableMapOf(
            typeOf<String>() to CanvasObjectStringConverter(),
            typeOf<Int>() to CanvasObjectIntConverter(),
            typeOf<List<Any>>() to SimpleListConverter<Any>("[","]")
        )
}