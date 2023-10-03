package m1k.kotik.negocards.data.serialization.value_converters

import java.lang.reflect.Type
import kotlin.reflect.KType
import kotlin.reflect.typeOf


class TestConverterSet: IValueConverterSet {
    private inline fun <reified T> getType() = T::class.java.componentType
    override val objectSeparator: String
        get() = ";"
    override val typeToConverterMap: Map<KType, IValueConverter<*>>
        get() = mutableMapOf(
            typeOf<String>() to SimpleValueConverter<String>("\"","\""),
            typeOf<Int>() to SimpleValueConverter<Int>("(", ")"),
            typeOf<List<Any>>() to SimpleListConverter<Any>("[","]")
        )
}