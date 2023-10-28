package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes

import android.graphics.Paint
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.CanvasObjectMode
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasDrawable
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasEditable
import m1k.kotik.negocards.data.serialization.serializationObject.SerializeMember

abstract class CanvasShape (
    // x, y - координаты верхнего левого угла для прямоугольника в который впиысывается фигура
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    override var paint: Paint

): CanvasObject(x,y,width,height), ICanvasDrawable, ICanvasEditable {
    companion object{
        const val defaultColor = "FF181818"
    }

    override var mode: CanvasObjectMode = CanvasObjectMode.None
        set(value) {
            if (field != CanvasObjectMode.ModeFree){
                field = value
            }
        }
}