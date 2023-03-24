package m1k.kotik.negocards.data.canvas_qrc.model

import android.graphics.Canvas
import android.graphics.Paint


class TextObject(
    var text: String,
    var fontSize: Int,
    color: String,
    posX: Int,
    posY: Int,
    style: Tag.Style
) : CanvasObject(ObjectType.Text(), (fontSize*text.length), fontSize,posX, posY,color,style) {
    constructor(): this(
        Tag.Text().default,
        Tag.FontSize().default,
        Tag.Color().default,
        Tag.PosX().default,
        Tag.PosY().default,
        Tag.Style.Fill())

    override fun draw(canvas: Canvas) {
        canvas.drawText(text, posX.toFloat(),posY.toFloat(),Paint().also {
            it.color = this.getParseColor()
            it.textSize = this.fontSize.toFloat()
        })
    }
}