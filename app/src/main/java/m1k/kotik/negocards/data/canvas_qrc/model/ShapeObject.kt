package m1k.kotik.negocards.data.canvas_qrc.model

import android.graphics.Canvas
import android.graphics.Paint

open class ShapeObject(
    var shapeType: CanvasObjectType.Shape,
    posX: Int = 0,
    posY: Int = 0,
    width: Int = 0,
    height: Int = 0,
    color:  String = "FF181818",
    style: CanvasObjectSerializationTag.Style,

    ) : CanvasObject(CanvasObjectType.Shape(), width, height, posX, posY,color,style) {
    constructor(): this(
        CanvasObjectType.Shape.Rect(),
        50,
        50,
        0,
        0,
        "FF181818",
        CanvasObjectSerializationTag.Style.Fill())

    override fun draw(canvas: Canvas) {
        canvas.drawRect(posX.toFloat(),posY.toFloat(),posX+width.toFloat(),posY+height.toFloat(),
            Paint().also{
                it.color =  this.getParseColor()
                it.style = this.style.sType
            })
    }
}