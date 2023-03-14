package m1k.kotik.negocards.data.canvas_qrc.model.shapes

import android.graphics.Color
import android.graphics.Paint.Style
import m1k.kotik.negocards.data.canvas_qrc.model.ShapeObject
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject.ObjectType.Shape

enum class ArcShapeValueTag (val tag: String) {
    ShapeType("st"),
    Color("cl"),
    Style("sl"),
    PosX("x"),
    PosY("y"),
    Width("w"),
    Height("h"),
    StartAngle("sa"),
    SweepAngle("ea"),
    UseCenter("uc")
}

class ArcShape(
    val startAngle: Int,
    val sweepAngle: Int,
    val useCenter:Boolean,

    posX: Int = 0,
    posY: Int = 0,
    width: Int = 0,
    height: Int = 0,
    color: String = "FF181818",
    style: Style
) : ShapeObject(Shape.Arc(), posX, posY, width, height,color,style) {

}