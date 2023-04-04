package m1k.kotik.negocards.data.canvas_qrc.model.shapes

import android.graphics.Canvas
import android.graphics.Paint
import m1k.kotik.negocards.data.canvas_qrc.model.ShapeObject

class RectShape(
    posX: Int,
    posY: Int,
    width: Int,
    height: Int,
    color: String,
    style: CanvasObjectSerializationTag.Style
) :ShapeObject(
    CanvasObjectType.Shape.Rect(),
    posX,
    posY,
    width,
    height,
    color,
    style){
    constructor(): this(
        100,
        100,
        100,100,
        "FF181818",
        CanvasObjectSerializationTag.Style.Fill())

    override fun draw(canvas: Canvas) {
        canvas.drawRect(posX.toFloat() ,posY.toFloat()+ height, posX+width.toFloat(),posY+height.toFloat(),
            Paint().also{
            it.color =  this.getParseColor()
            it.style = this.style.sType
        })
    }

}