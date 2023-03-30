package m1k.kotik.negocards.data.canvas_qrc.model.shapes

import android.graphics.Canvas
import m1k.kotik.negocards.data.canvas_qrc.model.ShapeObject
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject.CanvasObjectType.Shape

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
    ) : ShapeObject(Shape.Quadril(),bottomLeftX,bottomLeftY, width, height,color,style) {
    override fun draw(canvas: Canvas) {
        TODO("Not yet implemented")
    }


}