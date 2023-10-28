package m1k.kotik.negocards.data.canvas_qrc.canvas_serialization.canvas_converters

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.PathEffect
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.graphics.toColorInt
import m1k.kotik.negocards.data.serialization.value_converters.IValueConverter
import java.lang.StringBuilder
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class PaintConverter: IValueConverter<Paint> {
    override val valueStarts = "["
    override val valueEnds = "]"
    private val itemSeparator = ","
    private val keyValueSeparator = ":"
    //потом можно будет реализовать c - color, s - style, sw - strokeWidth

    override fun serialize(value: Any): String {
        var outputString = StringBuilder()
        outputString.append(valueStarts)
        value as Paint
        outputString.append("#" +Integer.toHexString(value.color) + itemSeparator)
        outputString.append(styleMap.keys.find{styleMap[it] == value.style})
        outputString.append(valueEnds)
        return outputString.toString()
    }
    val styleMap = mapOf<String, Style>(
        "s" to Style.STROKE,
        "f" to Style.FILL,
        "sf" to Style.FILL_AND_STROKE,
    )
    override fun deserialize(serializationValue: String): Paint {
        val list = serializationValue.drop(valueStarts.length).dropLast(valueEnds.length).split(itemSeparator)
        return Paint().also {
            it.color = list[0].toColorInt()
            it.style = styleMap[list[1]]
        }
    }

}