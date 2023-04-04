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
        CanvasObjectSerializationTag.PosX.default,
        CanvasObjectSerializationTag.PosY.default,
        CanvasObjectSerializationTag.WidthTag.default,
        CanvasObjectSerializationTag.Height.default,
        CanvasObjectSerializationTag.Color.default,
        CanvasObjectSerializationTag.Style().default)

    override fun draw(canvas: Canvas) {
        canvas.drawRect(posX.toFloat(),posY.toFloat()+height.toFloat(),posX+width.toFloat(),posY.toFloat(),
            Paint().also{
                it.color =  this.getParseColor()
                it.style = this.style.sType
                it.isAntiAlias = true
                it.isDither = true
        })
    }

}