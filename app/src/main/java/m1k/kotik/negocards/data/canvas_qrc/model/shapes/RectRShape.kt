package m1k.kotik.negocards.data.canvas_qrc.model.shapes

import android.graphics.Canvas
import android.graphics.Paint
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.model.ShapeObject
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject.ObjectType.Shape


class RectRShape (
    var leftCorner: Int,
    var rightCorner: Int,

    posX: Int,
    posY: Int,
    width: Int,
    height: Int,
    color: String,
    style: Tag.Style
) : ShapeObject(Shape.RectR(), posX, posY, width, height,color,style) {
    constructor(): this(0,0,100,100,100,100,"FF181818",Tag.Style.Fill())
    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(posX.toFloat(),posY.toFloat(),posX+width.toFloat(),posY+height.toFloat(),leftCorner.toFloat(),rightCorner.toFloat(), Paint().apply {
            color = this.color
            style = this.style})
    }

}