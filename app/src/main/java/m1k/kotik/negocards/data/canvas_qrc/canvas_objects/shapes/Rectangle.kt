package m1k.kotik.negocards.data.canvas_qrc.canvas_objects.shapes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style

class Rectangle(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    color: String = defaultColor

) : CanvasShape(x,y,width, height,color) {
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