package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes

import android.graphics.Canvas
import android.graphics.Paint
import m1k.kotik.negocards.data.serialization.serializationObject.SerializeMember

class RoundRectangle(
    x: Int = 0,
    y: Int = 0,
    width: Int = 100,
    height: Int = 100,
    @SerializeMember("rlc")
    var leftCorner: Int = 10,
    @SerializeMember("rrc")
    var rightCorner:Int = 10,
    paint: Paint = Paint()

) : BitmapShape(x,y,width, height, paint, 0) {
    override val key: String = "rr"
    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(
            x.toFloat() * zoomValue, //Левая стороная
            y.toFloat() * zoomValue, //Верхняя сторона
            (x + width.toFloat()) * zoomValue, //Правая сторона
            (y + height.toFloat())* zoomValue,
            leftCorner.toFloat(),
            rightCorner.toFloat(),
            paint
        )
    }
}