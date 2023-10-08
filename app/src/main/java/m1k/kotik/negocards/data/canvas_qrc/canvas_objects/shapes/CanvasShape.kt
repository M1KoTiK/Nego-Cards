package m1k.kotik.negocards.data.canvas_qrc.canvas_objects.shapes

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.graphics.toColorInt
import m1k.kotik.negocards.data.canvas_qrc.canvas_objects.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.canvas_objects.ICanvasDrawable
import m1k.kotik.negocards.data.serialization.serializationObject.SeriаlizationMember

abstract class CanvasShape(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    @SeriаlizationMember("clr")
    var color: String,

): CanvasObject(x,y,width,height), ICanvasDrawable {
protected val paint: Paint = Paint().also { it.color = color.toColorInt() }
}