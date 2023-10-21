package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.texts

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.graphics.toColorInt
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasDrawable
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.CanvasShape
import m1k.kotik.negocards.data.serialization.serializationObject.SeriаlizationMember

class CanvasText(
    // x, y - координаты верхнего левого угла для прямоугольника в который впиысывается фигура
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    @SeriаlizationMember("clr")
    var color: String = CanvasShape.defaultColor,
    var text: String = "text"

    ): CanvasObject(x,y,width,height), ICanvasDrawable {

    override val paint: Paint
        get() = Paint().also { it.color = color.toColorInt() }

    override fun draw(canvas: Canvas) {
        canvas.drawText(text,x.toFloat(),y.toFloat(),paint)
    }

    override val key: String
        get() = TODO("Not yet implemented")

}