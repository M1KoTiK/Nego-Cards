package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.texts

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import m1k.kotik.negocards.data.serialization.serializationObject.SerializeMember

class Phone(
    override val key: String = "ph",
    @SerializeMember("x")
    override var x: Int = 0,
    @SerializeMember("y")
    override var y: Int = 0,
    @SerializeMember("p")
    override var paint: Paint = Paint()
): ICanvasText {

    override fun draw(canvas: Canvas) {
        canvas.drawText(text, x.toFloat(), y.toFloat() + height,paint)
    }

    override var zoomValue: Float = 1f
    @SerializeMember("w")
    override var width: Int = 100
        set(value) {
            field = paint.measureText(text).toInt()
        }
    @SerializeMember("t")
    override var text: String = "number"
        set(value) {
            field = value
            width = paint.measureText(value).toInt()
        }

    @SerializeMember("h")
    override var height: Int = 100
        set(value) {
            paint.textSize = value.toFloat()
            val bounds: Rect = Rect()
            paint.getTextBounds(text, 0, text.length, bounds)
            val textHeight: Int = bounds.height()
            val offset = value - textHeight
            paint.textSize = value.toFloat() + offset
            field = value
        }
    fun textBounds(): Rect{
        val bounds: Rect = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        return bounds
    }
    init {
        paint.textSize = height.toFloat()
        val bounds: Rect = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        val textHeight: Int = bounds.height()
        val offset = height - textHeight
        paint.textSize = height.toFloat() + offset
        width = paint.measureText(text).toInt()
    }
}