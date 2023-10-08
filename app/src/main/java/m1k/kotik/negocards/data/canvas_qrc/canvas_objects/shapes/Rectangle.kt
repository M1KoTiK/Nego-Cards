package m1k.kotik.negocards.data.canvas_qrc.canvas_objects.shapes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style

class Rectangle(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    color: String

) : CanvasShape(x,y,width, height,color) {
    init {

    }

    override val key: String = "rc"

    override fun draw(canvas: Canvas) {
        TODO("Not yet implemented")
    }
}