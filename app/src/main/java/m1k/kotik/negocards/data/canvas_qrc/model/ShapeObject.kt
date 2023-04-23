package m1k.kotik.negocards.data.canvas_qrc.model

import android.graphics.*

open class ShapeObject(
    var shapeType: CanvasObjectType.Shape,
    posX: Int = 0,
    posY: Int = 0,
    width: Int = 0,
    height: Int = 0,
    color:  String = "FF181818",
    style: CanvasObjectSerializationTag.Style,
    var strokeWidth: Int = CanvasObject.CanvasObjectSerializationTag.StrokeWidth.default,
    var pathEffect: Int = CanvasObject.CanvasObjectSerializationTag.StrokeWidth.default

    ) : CanvasObject(CanvasObjectType.Shape(), width, height, posX, posY,color,style) {
    constructor(): this(
        CanvasObjectType.Shape.Rect(),
        50,
        50,
        0,
        0,
        "FF181818",
        CanvasObjectSerializationTag.Style.Fill(),
    )

    open val objectPaint: Paint = Paint().also {
        it.isDither = true
        it.isAntiAlias = true
        it.color =  this.getParseColor()
        it.style = this.style.sType
        it.strokeWidth = this.strokeWidth.toFloat()
    }
    override fun draw(canvas: Canvas) {
        canvas.drawRect(
            posX.toFloat(),
            posY.toFloat(),
            posX+width.toFloat(),
            posY+height.toFloat(),
            objectPaint)
    }

}