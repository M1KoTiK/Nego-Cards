package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.canvas_tools

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_editors.CanvasEditor
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasMeasurable
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Rectangle
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.ICanvasTool
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.TouchProcessingCanvasTool
import m1k.kotik.negocards.data.canvas_qrc.old_govno.canvas_object_types.ShapeObject
import m1k.kotik.negocards.data.measure_utils.displacementByDelta
import m1k.kotik.negocards.data.measure_utils.isClickOnObject

class CanvasPositionEditTool(override val canvasEditor: CanvasEditor) :
    TouchProcessingCanvasTool<ICanvasMeasurable>() {

    override var x: Int = 0
    override var y: Int = 0

    override var width: Int = 0
    override var height: Int = 0

    private var _objectForEdit: MutableList<ICanvasMeasurable> = mutableListOf()
    override var objectsForEdit: MutableList<ICanvasMeasurable>
        get() = _objectForEdit
        set(value) {
            if(value.isNotEmpty()) {
                _objectForEdit = value
                updateToolMeasures()
            }
        }

    private var initialObjectForEdit = mutableListOf<ICanvasMeasurable>()
    private var startX: Int = 0
    private var startY: Int = 0
    var isPressed = false
    override val onClickDown: (x: Float, y: Float) -> Unit ={ eventX, eventY ->
        startX = eventX.toInt()
        startY = eventY.toInt()
        initialObjectForEdit.clear()
        initialObjectForEdit = objectsForEdit.map{obj ->
            Rectangle().also { rc ->
                rc.x = obj.x
                rc.y = obj.y
                rc.width = obj.width
                rc.height = obj.height
            }
        }.toMutableList()
    }

    override val onMovingWhenPressed: (deltaX: Float, deltaY: Float, x: Float, y: Float) -> Unit = {
        deltaX, deltaY, _, _ ->
        for (i in 0 until initialObjectForEdit.count()){
            val obj = initialObjectForEdit[i]
            val displacementPoint = displacementByDelta(obj.x, obj.y, deltaX.toInt(), deltaY.toInt())
            objectsForEdit[i].x = displacementPoint.x
            objectsForEdit[i].y = displacementPoint.y
            updateToolMeasures()
        }
    }

    override fun draw(canvas: Canvas) {
        if(objectsForEdit.isNotEmpty()) {
            canvas.drawRoundRect(
                x.toFloat() - 10,
                y.toFloat() - 10,
                x.toFloat() + width.toFloat() + 10,
                y.toFloat() + height.toFloat() + 10,
                5f,
                5f,
                Paint().also {
                    it.color = Color.parseColor("#181818")
                    it.style = Paint.Style.STROKE
                    it.strokeWidth = 4f
//                    it.pathEffect = DashPathEffect(floatArrayOf(50f, 10f), 0f)
                })
        }
    }

    private fun updateToolMeasures(){
        val sortedByX = objectsForEdit.sortedBy { it.x }
        val sortedByY = objectsForEdit.sortedBy { it.y }
        val x = sortedByX.first().x
        val y = sortedByY.first().y
        val width = sortedByX.last().x + sortedByX.last().width - x
        val height = sortedByX.last().y + sortedByX.last().height - y
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    override var onPositioning: (List<ICanvasMeasurable>) -> Point = {
        Point()
    }


}