package m1k.kotik.negocards.data.canvas_qrc.model

import android.graphics.Color
import android.graphics.Paint

enum class ShapeType(val tag: String) {
    Rect("rc"),
    Line("ln"),
    Arc("ar"),
    RectRound("rr"),
    Oval("ov"),
    RectF("rf"),
    Quadril("qd")
}

class ShapeObject(
    val shapeType: ShapeType,
    val color:  Int = Color.BLACK,
    posX: Int = 0,
    posY: Int = 0,
    width: Int = 0,
    height: Int = 0,
    val leftCorner: Int = 0,
    val rightCorner: Int = 0,
    val useCenter: Boolean = false,

    val bottomLeftX: Int = 0,
    val bottomLeftY: Int = 0,
    val topLeftX: Int = 0,
    val topLeftY: Int = 0,
    val bottomRightX: Int = 0,
    val bottomRightY: Int = 0,
    val topRightX: Int = 0,
    val topRightY: Int = 0,

) : CanvasObject(ObjectType.Shape, width, height, posX, posY) {

}