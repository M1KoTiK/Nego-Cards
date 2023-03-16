package m1k.kotik.negocards.data.canvas_qrc.model

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint


class TextObject(
    var text: String,
    var fontSize: Int,
    color: String,
    posX: Int,
    posY: Int
) : CanvasObject(ObjectType.Text(), (fontSize*text.length), fontSize,posX, posY,color) {
    override fun draw(canvas: Canvas) {
        canvas.drawText(text, posX.toFloat(),posY.toFloat(),Paint().also {
            it.color = this.getParseColor()
            it.textSize = this.fontSize.toFloat()
        })
    }
}