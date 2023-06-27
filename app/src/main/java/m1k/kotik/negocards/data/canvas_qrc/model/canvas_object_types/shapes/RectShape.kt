package m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.shapes

import android.graphics.Canvas
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.CanvasObjectSerializationTag
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.CanvasObjectType
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.ShapeObject

class RectShape(
    posX: Int,
    posY: Int,
    width: Int,
    height: Int,
    color: String,
    style: CanvasObjectSerializationTag.Style,
    strokeWidth: Int = CanvasObjectSerializationTag.StrokeWidth.default
) : ShapeObject(
    CanvasObjectType.Shape.Rect(),
    posX,
    posY,
    width,
    height,
    color,
    style,
    strokeWidth
    ){
    constructor(): this(
        CanvasObjectSerializationTag.PosX.default,
        CanvasObjectSerializationTag.PosY.default,
        CanvasObjectSerializationTag.WidthTag.default,
        CanvasObjectSerializationTag.Height.default,
        CanvasObjectSerializationTag.Color.default,
        CanvasObjectSerializationTag.Style().default,
        CanvasObjectSerializationTag.StrokeWidth.default
    )

    override fun draw(canvas: Canvas) {
        canvas.drawRect(
            posX.toFloat(),
            posY.toFloat()+height.toFloat(),
            posX+width.toFloat(),
            posY.toFloat(),
            objectPaint)

    }

}