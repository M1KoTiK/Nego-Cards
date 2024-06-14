package m1k.kotik.negocards.data.canvas_qrc.canvas_serialization.canvas_converters

import android.graphics.Paint
import android.graphics.Paint.Style
import androidx.core.graphics.toColorInt
import m1k.kotik.negocards.data.serialization.value_converters.IValueConverter
import java.lang.StringBuilder

class PaintConverter: IValueConverter<Paint> {
    override val valueStarts = "["
    override val valueEnds = "]"
    private val itemSeparator = ","
    //потом можно будет реализовать c - color, s - style, sw - strokeWidth

    override fun serialize(value: Any): String {
        var outputString = StringBuilder()
        outputString.append(valueStarts)
        value as Paint
        val defaultPaint = Paint()
        if (value.color != defaultPaint.color) outputString.append("c:" + "#" + Integer.toHexString(value.color) + itemSeparator)
        if(value.style != defaultPaint.style) outputString.append("s:" + styleMap.keys.find{styleMap[it] == value.style} + itemSeparator)
        if(value.textSize != defaultPaint.textSize) outputString.append("ts:" + value.textSize.toInt())
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
        val outPaint = Paint()
        if (list.isNotEmpty()) {
            list.forEach{
                if(it.startsWith("c:")){
                    outPaint.color = it.drop(2).toColorInt()
                }
                else if(it.startsWith("s:")){
                    outPaint.style = styleMap[it.drop(2)]
                }
                else if (it.startsWith("ts:")){
                    outPaint.textSize = it.drop(3).toFloat()
                }
            }

        }
        return outPaint
    }

}