package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.texts

import android.graphics.Canvas
import android.graphics.Paint
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.*
import m1k.kotik.negocards.data.serialization.serializationObject.SerializeMember

class CanvasText(
    @SerializeMember("x")
    override var x: Int = 0,
    @SerializeMember("y")
    override var y: Int = 0,
    @SerializeMember("w")
    override var width: Int = 100,
    @SerializeMember("h")
    override var height: Int = 100,
    @SerializeMember("t")
    override var text: String = "Текст",
    @SerializeMember("p")
    override var paint: Paint = Paint()
    ): ICanvasDrawable, ICanvasZoomable, ICanvasMeasurable, ICanvasTextInputable {

    override fun draw(canvas: Canvas) {
        canvas.drawText(text, x.toFloat(),y.toFloat(),paint)
    }

    override var zoomValue: Float
        get() = 1f
        set(value) {}

}