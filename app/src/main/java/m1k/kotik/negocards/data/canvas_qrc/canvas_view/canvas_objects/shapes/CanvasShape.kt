package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes

import android.graphics.Paint
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.*

abstract class CanvasShape (
    // x, y - координаты верхнего левого угла для прямоугольника в который впиысывается фигура
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    override var paint: Paint

): CanvasObject(x,y,width,height), ICanvasDrawable, ICanvasEditable, ICanvasZoomable {
    companion object{
        const val defaultColor = "FF181818"
    }

    override var zoomValue = 1f
    override var mode = CanvasObjectMode.None
}