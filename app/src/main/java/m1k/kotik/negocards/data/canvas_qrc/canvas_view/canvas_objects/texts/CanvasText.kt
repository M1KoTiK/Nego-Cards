package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.texts

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.graphics.toColorInt
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasDrawable
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.CanvasShape
import m1k.kotik.negocards.data.serialization.serializationObject.SerializeMember

class CanvasText(
    // x, y - координаты верхнего левого угла для прямоугольника в который впиысывается фигура
    x: Int = 0,
    y: Int = 0,
    width: Int = 100,
    height: Int = 100,
    @SerializeMember("t")
    var text: String = "text",
    @SerializeMember("p")
    override var paint: Paint = Paint()
    ): CanvasObject(x,y,width,height), ICanvasDrawable {

    override fun draw(canvas: Canvas) {
        canvas.drawText(text,x.toFloat(),y.toFloat(),paint)
    }

    override val key: String
        get() = TODO("Not yet implemented")

}