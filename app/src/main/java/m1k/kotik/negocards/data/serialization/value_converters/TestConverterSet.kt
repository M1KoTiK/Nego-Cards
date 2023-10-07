package m1k.kotik.negocards.data.serialization.value_converters

import m1k.kotik.negocards.data.serialization.value_converters.default_converters.BoolConverter
import m1k.kotik.negocards.data.serialization.value_converters.default_converters.IntConverter
import m1k.kotik.negocards.data.serialization.value_converters.default_converters.StringConverter
import kotlin.reflect.KType
import kotlin.reflect.typeOf


class TestConverterSet: IValueConverterSet {

    override val objectSeparator: String
        get() = ";"
    override val typeToConverterMap: Map<KType, IValueConverter<*>>
        get() = mutableMapOf(
            typeOf<String>() to StringConverter(),
            typeOf<Int>() to IntConverter(),
            typeOf<Boolean>() to BoolConverter(),
            typeOf<List<Any>>() to SimpleListConverter<Any>()
        )
}