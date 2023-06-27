package m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.shapes

import android.graphics.Canvas
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.CanvasObjectSerializationTag
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.CanvasObjectType
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.ShapeObject
class ArcShape(
    var startAngle: Int,
    var sweepAngle: Int,
    var useCenter:Boolean,

    posX: Int = 0,
    posY: Int = 0,
    width: Int = 0,
    height: Int = 0,
    color: String = "FF181818",
    style: CanvasObjectSerializationTag.Style
) : ShapeObject(CanvasObjectType.Shape.Arc(), posX, posY, width, height,color,style) {
    constructor(): this(
        CanvasObjectSerializationTag.StartAngle.default,
        CanvasObjectSerializationTag.SweepAngle.default,
        CanvasObjectSerializationTag.UseCenter.default,
        CanvasObjectSerializationTag.PosX.default,
        CanvasObjectSerializationTag.PosY.default,
        CanvasObjectSerializationTag.WidthTag.default,
        CanvasObjectSerializationTag.Height.default,
        CanvasObjectSerializationTag.Color.default,
        CanvasObjectSerializationTag.Style.Fill())
    override fun draw(canvas: Canvas) {
        canvas.drawArc(
            posX.toFloat(),
            posY.toFloat(),
            posX+width.toFloat(),
            posY+height.toFloat(),
            startAngle.toFloat(),
            sweepAngle.toFloat(),
            useCenter,
            objectPaint)
    }

}