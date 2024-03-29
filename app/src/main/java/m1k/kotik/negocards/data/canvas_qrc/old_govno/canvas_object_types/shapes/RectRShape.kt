package m1k.kotik.negocards.data.canvas_qrc.old_govno.canvas_object_types.shapes

import android.graphics.Canvas
import android.graphics.Paint
import m1k.kotik.negocards.data.canvas_qrc.old_govno.canvas_object_types.CanvasObjectSerializationTag
import m1k.kotik.negocards.data.canvas_qrc.old_govno.canvas_object_types.CanvasObjectType
import m1k.kotik.negocards.data.canvas_qrc.old_govno.canvas_object_types.ShapeObject


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
) : ShapeObject(CanvasObjectType.Shape.RectR(), posX, posY, width, height,color,style,strokeWidth) {
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
        canvas.drawRoundRect(posX.toFloat(),
            posY.toFloat(),
            posX + width.toFloat(),
            posY + height.toFloat(),
            leftCorner.toFloat(),
            rightCorner.toFloat(),
            objectPaint)
    }
        override fun drawWithCustomPaint(canvas:Canvas, paint: Paint){
        canvas.drawRoundRect(posX.toFloat(),posY.toFloat(),
            posX+width.toFloat(),posY+height.toFloat(),
            leftCorner.toFloat(),
            rightCorner.toFloat(),
            paint)
    }

}