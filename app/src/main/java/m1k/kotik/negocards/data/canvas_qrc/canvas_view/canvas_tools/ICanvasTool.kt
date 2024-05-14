package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_editors.CanvasEditor
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasMeasurable
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasClickProcessed
interface ICanvasTool<T>: ICanvasClickProcessed, ICanvasMeasurable {
    fun draw(canvas: Canvas)
    val canvasEditor: CanvasEditor
    var objectsForEdit: MutableList<T>
    var onPositioning: (List<T>)-> Point
}