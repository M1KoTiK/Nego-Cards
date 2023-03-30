package m1k.kotik.negocards.data.canvas_qrc.model

import android.graphics.Canvas
import android.graphics.Paint


class TextObject(
    var text: String,
    var fontSize: Int,
    color: String,
    posX: Int,
    posY: Int,
    style: CanvasObjectSerializationTag.Style
) : CanvasObject(CanvasObjectType.Text(), ((fontSize+10)*text.length), (fontSize* 1.5).toInt(),posX, posY,color,style) {
    constructor(): this(
        CanvasObjectSerializationTag.Text().default,
        CanvasObjectSerializationTag.FontSize().default,
        CanvasObjectSerializationTag.Color().default,
        CanvasObjectSerializationTag.PosX.default,
        CanvasObjectSerializationTag.PosY().default,
        CanvasObjectSerializationTag.Style.Fill())

    override fun draw(canvas: Canvas) {
        canvas.drawText(text, posX.toFloat(),posY.toFloat(),Paint().also {
            it.color = this.getParseColor()
            it.textSize = this.fontSize.toFloat()
        })
    }
}