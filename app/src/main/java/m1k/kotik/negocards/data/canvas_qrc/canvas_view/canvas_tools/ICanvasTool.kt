package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools

import android.graphics.Canvas
import android.graphics.Point
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasBitmapMeasurable
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasClickProcessed
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasPositionable
import kotlin.reflect.KType

interface ICanvasTool<T>: ICanvasPositionable, ICanvasClickProcessed, ICanvasBitmapMeasurable {
    fun draw(canvas: Canvas)
    var objectsForEdit: List<T>
    var onCanvasPositioning: ()-> Point
}