package m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.shapes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.CanvasObjectSerializationTag
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.CanvasObjectType
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.ShapeObject

class QadrilShape (
    val bottomLeftX: Int = 0,
    val bottomLeftY: Int = 0,
    val topLeftX: Int = 0,
    val topLeftY: Int = 0,
    val bottomRightX: Int = 0,
    val bottomRightY: Int = 0,
    val topRightX: Int = 0,
    val topRightY: Int = 0,
    posX: Int = 0,
    posY: Int = 0,
    width: Int = 0,
    height: Int = 0,
    color: String = "FF181818",
    style: CanvasObjectSerializationTag.Style
    ) : ShapeObject(CanvasObjectType.Shape.Quadril(),bottomLeftX,bottomLeftY, width, height,color,style) {
    override fun draw(canvas: Canvas) {
        val path: Path = Path()
        path.moveTo(posX.toFloat(), posY.toFloat()) // Top
        path.lineTo(bottomLeftX.toFloat(), bottomLeftY.toFloat())
        path.lineTo(topLeftX.toFloat(), topLeftY.toFloat())
        path.lineTo(topRightX.toFloat(), topRightY.toFloat())
        path.lineTo(bottomRightX.toFloat(), bottomRightY.toFloat())
        path.close();
        canvas.drawPath(path, Paint().also {
            it.color = this.getParseColor()
            it.style = this.style.sType})
        }
    }