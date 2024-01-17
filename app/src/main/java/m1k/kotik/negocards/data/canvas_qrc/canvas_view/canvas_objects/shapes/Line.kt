package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path

class Line(
    var x1: Int = 0,
    var y1: Int = 0,
    var x2: Int = 100,
    var y2: Int = 100,
    paint: Paint = Paint()

) : CanvasShape(x1,y1,x2-x1, y2-y1, paint) {
    override val key: String = "ln"
    private val path = Path()
    override fun draw(canvas: Canvas) {
        path.reset()
        path.moveTo(x1.toFloat(),y1.toFloat())
        path.lineTo(x2.toFloat(),y2.toFloat())
        canvas.drawPath(path, paint)
    }
}