package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects

import android.graphics.Canvas
import android.graphics.Paint
import m1k.kotik.negocards.data.serialization.serializationObject.SerializeMember

interface ICanvasDrawable {
    @SerializeMember("p")
    var paint: Paint
    fun draw(canvas: Canvas)
}