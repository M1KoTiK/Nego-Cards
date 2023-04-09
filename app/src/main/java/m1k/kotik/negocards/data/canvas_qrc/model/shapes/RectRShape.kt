package m1k.kotik.negocards.data.canvas_qrc.model.shapes

import android.graphics.Canvas
import android.graphics.Paint
import m1k.kotik.negocards.data.canvas_qrc.model.ShapeObject
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject.CanvasObjectType.Shape


class RectRShape (
    var leftCorner: Int,
    var rightCorner: Int,
    posX: Int,
    posY: Int,
    width: Int,
    height: Int,
    color: String,
    style: CanvasObjectSerializationTag.Style,
    strokeWidth: Int = CanvasObjectSerializationTag.StrokeWidth.default
) : ShapeObject(Shape.RectR(), posX, posY, width, height,color,style,strokeWidth) {
    constructor(): this(
        CanvasObjectSerializationTag.LeftCorner.default,
        CanvasObjectSerializationTag.RightCorner.default,
        CanvasObjectSerializationTag.PosX.default,
        CanvasObjectSerializationTag.PosY.default,
        CanvasObjectSerializationTag.WidthTag.default,
        CanvasObjectSerializationTag.Height.default,
        CanvasObjectSerializationTag.Color.default,
        CanvasObjectSerializationTag.Style.Fill())
    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(posX.toFloat(),posY.toFloat(),posX+width.toFloat(),posY+height.toFloat(),leftCorner.toFloat(),rightCorner.toFloat(), Paint().also {
            it.color = this.getParseColor()
            it.style = this.style.sType
            it.isAntiAlias = true
            it.isDither = true
        })
    }
    fun drawRectRWithCustomPaint(canvas: Canvas,paint: Paint){
        canvas.drawRoundRect(posX.toFloat(),posY.toFloat(),
            posX+width.toFloat(),posY+height.toFloat(),
            leftCorner.toFloat(),
            rightCorner.toFloat(),
            paint)
    }

}