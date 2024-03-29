package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes

import android.graphics.Canvas
import android.graphics.Paint
import m1k.kotik.negocards.data.serialization.serializationObject.SerializeMember

class Rectangle(
    x: Int = 0,
    y: Int = 0,
    width: Int = 100,
    height: Int = 100,
    paint: Paint = Paint()

) : CanvasShape(x,y,width, height, paint) {
    override val key: String = "rc"

    override fun draw(canvas: Canvas) {
        canvas.drawRect(
            x.toFloat() * zoomValue, //Левая стороная
            y.toFloat() * zoomValue, //Верхняя сторона
            (x + width.toFloat()) * zoomValue, //Правая сторона
            (y + height.toFloat())* zoomValue, //Нижняя сторона
            paint // Кисть для задания стиля
        )
    }
}