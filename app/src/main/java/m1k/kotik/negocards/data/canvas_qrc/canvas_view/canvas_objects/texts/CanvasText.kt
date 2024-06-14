package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.texts

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.*
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.serializationObject.SerializeMember
import kotlin.math.abs

class CanvasText(
    override val key: String = "ctx",
    @SerializeMember("x")
    override var x: Int = 0,
    @SerializeMember("y")
    override var y: Int = 0,
    @SerializeMember("p")
    override var paint: Paint = Paint()
): ICanvasDrawable, ICanvasZoomable, ICanvasMeasurable, ICanvasTextInputable, ISerializationObject {

    override fun draw(canvas: Canvas) {
        canvas.drawText(text, x.toFloat(), y.toFloat() + height,paint)
    }

    override var zoomValue: Float
        get() = 1f
        set(value) {}
    @SerializeMember("w")
    override var width: Int = 100
        set(value) {
            field = paint.measureText(text).toInt()
        }
    @SerializeMember("t")
    override var text: String = "text"
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
    private fun textBounds(): Rect{
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