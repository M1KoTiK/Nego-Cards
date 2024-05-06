package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes

import android.graphics.Paint
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.*
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.serializationObject.SerializeMember

/**
 * Абстрактный класс представляющий фигуры которые могут быть на канвасе
 */
abstract class BitmapShape (
    @SerializeMember("x")
    override var x: Int,
    @SerializeMember("y")
    override var y: Int,
    @SerializeMember("w")
    override var width: Int,
    @SerializeMember("h")
    override var height: Int,
    @SerializeMember("p")
    override var paint: Paint

): ICanvasDrawable, ICanvasZoomable, ICanvasMeasurable, ISerializationObject, ICanvasSelectable {
    companion object{
        const val defaultColor = "FF181818"
    }

    override var isSelected: Boolean = false
    override var zoomValue = 1f
}