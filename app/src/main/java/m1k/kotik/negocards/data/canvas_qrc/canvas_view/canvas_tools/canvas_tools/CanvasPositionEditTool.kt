package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.canvas_tools

import android.graphics.Canvas
import android.graphics.Point
import android.view.MotionEvent
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasDrawable
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasPositionable
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.ICanvasTool
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class CanvasPositionEditTool() : ICanvasTool<ICanvasPositionable> {

    override var x: Int = 0
    override var y: Int = 0
    override var width: Int = 0
    override var height: Int = 0

    override var objectsForEdit: List<ICanvasPositionable>
        get() = TODO("Not yet implemented")
        set(value) {}

    override var onTouchEvent: (MotionEvent) -> Boolean = {
        val x = it.x.toInt()
        val y = it.y.toInt()
        true
    }

    override fun draw(canvas: Canvas) {

    }

    override var onCanvasPositioning: () -> Point
        get() = TODO("Not yet implemented")
        set(value) {}
}