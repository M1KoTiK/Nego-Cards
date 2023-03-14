package m1k.kotik.negocards.data.canvas_qrc.model

import android.graphics.Color
import android.graphics.Paint
import java.util.*

enum class ShapeType(val tag: String) {
    Rect("rc"),
    Line("ln"),
    Arc("ar"),
    RectRound("rr"),
    Oval("ov"),
    Quadril("qd")
}

open class ShapeObject(
    var shapeType: ObjectType.Shape,
    posX: Int = 0,
    posY: Int = 0,
    width: Int = 0,
    height: Int = 0,
    color:  String = "FF181818",
    val style: Paint.Style = Paint.Style.FILL,

    ) : CanvasObject(ObjectType.Shape(), width, height, posX, posY,color) {


    override fun decode(encodeString: String): CanvasObject {
        var textTag = ""
        var canvasObject: CanvasObject
        canvasObject = ShapeObject(ObjectType.Shape.Arc())
        for (char in encodeString){
            while(char!='\"' && char.toString().toDoubleOrNull()==null){

            }
        }
        return canvasObject
    }
}