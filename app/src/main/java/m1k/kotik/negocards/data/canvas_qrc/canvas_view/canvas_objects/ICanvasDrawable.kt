package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects

import android.graphics.Canvas
import android.graphics.Paint
import m1k.kotik.negocards.data.serialization.serializationObject.SerializeMember

/**
 * Представляет тип объектов которые могут быть отрисованы на канвасе.
 *
 * !Очень важно что у каждого объекта переопределяющего paint
 * нужно проставлять отрибут @SerializeMember("p") иначе он не будет сериализоваться!
 */
interface ICanvasDrawable {
    @SerializeMember("p")
    var paint: Paint
    fun draw(canvas: Canvas)
}