package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes

import android.graphics.Paint
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.*
import m1k.kotik.negocards.data.serialization.serializationObject.SerializeMember

/**
 * Абстрактный класс представляющий фигуры которые могут быть на канвасе
 */
abstract class CanvasShape (
    x: Int,
    y: Int,
    width: Int,
    /**
     *  //Высота прямоугольника в который вписывается фигура
     */
    height: Int,
    /**
     * Кисть необходима для отрисовки фигур из стандартной библиотеки
     */
    @SerializeMember("p")
    override var paint: Paint

): CanvasObject(x,y,width,height), ICanvasDrawable, ICanvasEditable, ICanvasZoomable {
    companion object{
        const val defaultColor = "FF181818"
    }

    override var zoomValue = 1f
    override var mode = CanvasObjectMode.None
}