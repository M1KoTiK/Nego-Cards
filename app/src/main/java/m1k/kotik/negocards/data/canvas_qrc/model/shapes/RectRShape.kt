package m1k.kotik.negocards.data.canvas_qrc.model.shapes

import android.graphics.Color
import android.graphics.Paint
import m1k.kotik.negocards.data.canvas_qrc.model.ShapeObject
import m1k.kotik.negocards.data.canvas_qrc.model.ShapeType

enum class RectRShapeValueTag (val tag: String) {
    ShapeType("st"),
    Color("cl"),
    Style("sl"),
    PosX("x"),
    PosY("y"),
    Width("w"),
    Height("h"),
    LeftCorner("lc"),
    RightCorner("rc")
}

class RectRShape (
    val leftCorner: Int,
    val rightCorner: Int,

    posX: Int = 0,
    posY: Int = 0,
    width: Int = 0,
    height: Int = 0,
    color: Int = Color.BLACK,
    style: Paint.Style
) : ShapeObject(ShapeType.RectRound, posX, posY, width, height,color,style) {

}