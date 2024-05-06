package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.canvas_tools

import android.graphics.*
import android.view.MotionEvent
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasMeasurable
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Rectangle
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.ICanvasTool
import m1k.kotik.negocards.data.canvas_qrc.old_govno.canvas_object_types.ShapeObject
import m1k.kotik.negocards.data.measure_utils.displacementByDelta
import m1k.kotik.negocards.data.measure_utils.isClickOnObject

class CanvasPositionEditTool : ICanvasTool<ICanvasMeasurable> {

    override var x: Int = 0
    override var y: Int = 0

    override var width: Int = 0

    override var height: Int = 0

    private var _objectForEdit: List<ICanvasMeasurable> = listOf()
    override var objectsForEdit: List<ICanvasMeasurable>
        get() = _objectForEdit
        set(value) {
            if(value.isNotEmpty()) {
                _objectForEdit = value
                updateToolMeasures()
            }
        }

    var initialObjectForEdit = mutableListOf<ICanvasMeasurable>()
    private var startX: Int = 0
    private var startY: Int = 0
    override var onTouchEvent: (MotionEvent) -> Boolean = {
        val deltaX:Int  = it.x.toInt() - startX
        val deltaY:Int = it.y.toInt() - startY

//        && isClickOnObject(this.x, this.y , this.width, this.height, it.x.toInt(), it.y.toInt())
        if (objectsForEdit.isNotEmpty()) {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = it.x.toInt()
                    startY = it.y.toInt()
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
                MotionEvent.ACTION_UP -> {

                }
                MotionEvent.ACTION_MOVE -> {
                    for (i in 0 until initialObjectForEdit.count()){
                        val obj = initialObjectForEdit[i]
                        println("objX = ${obj.x}")
                        val displacementPoint = displacementByDelta(obj.x, obj.y, deltaX, deltaY)
                        println("${it.x.toInt()} or ${displacementPoint.x}")
                        objectsForEdit[i].x = displacementPoint.x
                        objectsForEdit[i].y = displacementPoint.y
                        updateToolMeasures()
                    }
                }
            }
        }
        true
    }

    override fun draw(canvas: Canvas) {
        if(objectsForEdit.isNotEmpty()) {
            canvas.drawRoundRect(
                x.toFloat() - 5,
                y.toFloat() - 5,
                width.toFloat() + 5,
                height.toFloat() + 5,
                5f,
                5f,
                Paint().also {
                    it.color = Color.parseColor("#5E97FF")
                    it.style = Paint.Style.STROKE
                    it.strokeWidth = 5f
                    it.pathEffect = DashPathEffect(floatArrayOf(30f, 8f), 0f)
                })
        }
    }

    private fun updateToolMeasures(){
        val sortedByX = objectsForEdit.sortedBy { it.x }
        val sortedByY = objectsForEdit.sortedBy { it.y }
        val x = sortedByX.first().x
        val y = sortedByY.first().y
        val width = sortedByX.last().x + sortedByX.last().width
        val height = sortedByX.last().y + sortedByX.last().height
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }
}