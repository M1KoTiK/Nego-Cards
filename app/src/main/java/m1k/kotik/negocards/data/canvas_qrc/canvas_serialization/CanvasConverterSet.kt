package m1k.kotik.negocards.data.canvas_qrc.canvas_serialization

import android.graphics.Paint
import m1k.kotik.negocards.data.canvas_qrc.canvas_serialization.canvas_converters.PaintConverter
import m1k.kotik.negocards.data.serialization.value_converters.IValueConverter
import m1k.kotik.negocards.data.serialization.value_converters.IValueConverterSet
import m1k.kotik.negocards.data.serialization.value_converters.default_converters.BoolConverter
import m1k.kotik.negocards.data.serialization.value_converters.default_converters.IntConverter
import m1k.kotik.negocards.data.serialization.value_converters.default_converters.StringConverter
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class CanvasConverterSet: IValueConverterSet {
    override var splitSign: String = "->"
    override var objectSeparator: String = ";"
    override val typeToConverterMap: MutableMap<KType, IValueConverter<*>>
        get() = mutableMapOf(
            typeOf<String>() to StringConverter(),
            typeOf<Int>() to  IntConverter(),
            typeOf<Boolean>() to BoolConverter(),
            typeOf<Paint>() to PaintConverter()
        )

}