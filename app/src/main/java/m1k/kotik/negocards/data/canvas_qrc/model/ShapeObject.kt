package m1k.kotik.negocards.data.canvas_qrc.model

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import java.util.*
open class ShapeObject(
    var shapeType: ObjectType.Shape,
    posX: Int = 0,
    posY: Int = 0,
    width: Int = 0,
    height: Int = 0,
    color:  String = "FF181818",
    style: Tag.Style,

    ) : CanvasObject(ObjectType.Shape(), width, height, posX, posY,color,style) {


    override fun draw(canvas: Canvas) {
        TODO("Not yet implemented")
    }
}