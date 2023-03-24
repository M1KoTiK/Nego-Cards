package m1k.kotik.negocards.data.canvas_qrc.model.shapes

import android.graphics.Canvas
import android.graphics.Paint
import m1k.kotik.negocards.data.canvas_qrc.model.ShapeObject
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject.ObjectType.Shape
class ArcShape(
    var startAngle: Int,
    var sweepAngle: Int,
    val useCenter:Boolean,

    posX: Int = 0,
    posY: Int = 0,
    width: Int = 0,
    height: Int = 0,
    color: String = "FF181818",
    style: Tag.Style
) : ShapeObject(Shape.Arc(), posX, posY, width, height,color,style) {
    override fun draw(canvas: Canvas) {
        canvas.drawArc(posX.toFloat(),posY.toFloat(),posX+width.toFloat(),posY+height.toFloat(),startAngle.toFloat(),sweepAngle.toFloat(),useCenter, Paint().also {
            it.color = this.getParseColor()
            it.style = this.style.sType})
    }

}