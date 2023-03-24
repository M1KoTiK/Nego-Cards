package m1k.kotik.negocards.data.canvas_qrc.model.shapes

import android.graphics.Canvas
import android.graphics.Paint
import m1k.kotik.negocards.data.canvas_qrc.model.ShapeObject

class OvalShape {
    class Rect( posX: Int,
                posY: Int ,
                width: Int ,
                height: Int,
                color: String,
                style: Tag.Style) : ShapeObject(
        ObjectType.Shape.Rect(),
        posX,
        posY,
        width,
        height,
        color,
        style){
        override fun draw(canvas: Canvas) {
            canvas.drawOval(posX.toFloat(),posY.toFloat(),posX+width.toFloat(),posY+height.toFloat(),
                Paint().also{
                    it.color =  this.getParseColor()
                    it.style = this.style.sType
                })
        }

    }
}