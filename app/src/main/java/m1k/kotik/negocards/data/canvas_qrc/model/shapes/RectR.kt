package m1k.kotik.negocards.data.canvas_qrc.model.shapes

import android.graphics.Canvas
import android.graphics.Paint
import m1k.kotik.negocards.data.canvas_qrc.model.ShapeObject
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject.ObjectType.Shape


class RectR (
    val leftCorner: Int,
    val rightCorner: Int,

    posX: Int,
    posY: Int ,
    width: Int ,
    height: Int,
    color: String,
    style: Tag.Style
) : ShapeObject(Shape.RectR(), posX, posY, width, height,color,style) {
    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(posX.toFloat(),posY.toFloat(),posX+width.toFloat(),posY+height.toFloat(),leftCorner.toFloat(),rightCorner.toFloat(), Paint().apply {
            color = this.color
            style = this.style})
    }

}