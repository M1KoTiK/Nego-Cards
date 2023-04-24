package m1k.kotik.negocards.data.canvas_qrc.model

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import kotlin.math.abs


class TextObject(
    var text: String,
    var fontSize: Int,
    color: String,
    posX: Int,
    posY: Int,
    style: CanvasObjectSerializationTag.Style
) : CanvasObject(
    CanvasObjectType.Text,
    Paint().also{ it.textSize = fontSize.toFloat() }.measureText(text).toInt(),
    abs(Paint().also{ it.textSize = fontSize.toFloat() }.fontMetrics.ascent).toInt(),
    posX,
    posY,
    color,
    style) {
    constructor(): this(
        CanvasObjectSerializationTag.Text.default,
        CanvasObjectSerializationTag.FontSize().default,
        CanvasObjectSerializationTag.Color.default,
        CanvasObjectSerializationTag.PosX.default,
        CanvasObjectSerializationTag.PosY.default,
        CanvasObjectSerializationTag.Style.Fill())

    override fun draw(canvas: Canvas) {
        canvas.drawText(text, posX.toFloat(),posY.toFloat(),Paint().also {
            it.color = this.getParseColor()
            it.textSize = this.fontSize.toFloat()
        })
    }

    override val centerY: Int get() = posY - height/2

    override fun reMeasure(){
        this.apply {
            this.width = Paint().also{ it.textSize = fontSize.toFloat() }.measureText(text).toInt()
            this.height = abs(Paint().also{ it.textSize = fontSize.toFloat() }.fontMetrics.ascent).toInt()
        }

    }
    override fun isCursorHoveredOver(x: Int, y: Int): Boolean {
        if(x>posX && x < posX+width&& y > posY-height && y < posY){
            return true
        }
        return false
    }
    override fun move(x: Int,y: Int){
        this.posX = x - this.width / 2
        this.posY = y + this.height / 2
    }
}