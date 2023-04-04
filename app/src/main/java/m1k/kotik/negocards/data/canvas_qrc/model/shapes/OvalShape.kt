package m1k.kotik.negocards.data.canvas_qrc.model.shapes

import android.graphics.Canvas
import android.graphics.Paint
import m1k.kotik.negocards.data.canvas_qrc.model.ShapeObject

class OvalShape (
    posX: Int,
    posY: Int,
    width: Int,
    height: Int,
    color: String,
    style: CanvasObjectSerializationTag.Style) : ShapeObject(
        CanvasObjectType.Shape.Oval(),
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
            CanvasObjectSerializationTag.Style.Fill())
        override fun draw(canvas: Canvas) {
            canvas.drawOval(posX.toFloat(),posY.toFloat(),posX+width.toFloat(),posY+height.toFloat(),
                Paint().also{
                    it.color =  this.getParseColor()
                    it.style = this.style.sType
                    it.isAntiAlias = true
                    it.isDither = true
                })
            }
        }
