package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style

class Rectangle(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    override val paint: Paint

) : CanvasShape(x,y,width, height, paint) {
    override val key: String = "rc"

    override fun draw(canvas: Canvas) {
        canvas.drawRect(
            x.toFloat(), //Левая стороная
            y.toFloat(), //Верхняя сторона
            x + width.toFloat(), //Правая сторона
            y + height.toFloat(), //Нижняя сторона
            paint // Кисть для задания стиля
        )
    }
}