package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.canvas_tools

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.view.MotionEvent
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_editors.CanvasEditor
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasMeasurable
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.BitmapShape
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.ICanvasTool
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.canvas_multi_tools.CanvasMultiTool
import m1k.kotik.negocards.data.measure_utils.getRectForOccupiedSpace
import m1k.kotik.negocards.data.measure_utils.isClickOnObject

class BitmapShapeModifyTool(override val canvasEditor: CanvasEditor) : CanvasMultiTool<BitmapShape>() {
    override val onTouchEvent: (event: MotionEvent) -> Boolean= {
        if (objectsForEdit.isNotEmpty()) {
            if (!measureTool.onTouchEvent(it)) {
                deleteTool.isVisible = true
                if (!deleteTool.onTouchEvent(it)) {
                    measureTool.isVisible = true
                    if (positionTool.onTouchEvent(it)) {
                        measureTool.isVisible = false
                        deleteTool.isVisible = false
                    } else {
                        measureTool.isVisible = true
                        deleteTool.isVisible = true
                    }
                } else {
                    measureTool.isVisible = false
                }
            } else {
                deleteTool.isVisible = false
            }
        }
        else{
            objectsForEdit.clear()
            canvasEditor.invalidate()
        }
            false
        }

    override var x: Int = 0
    override var y: Int =0
    override var width: Int = 100
    override var height: Int = 100
    private val deleteTool = DeleteObjectTool<BitmapShape>(canvasEditor)
    private val measureTool = CanvasMeasureEditTool(canvasEditor)
    private val positionTool = CanvasPositionEditTool(canvasEditor)
    private val testTool = EditObjectTool<BitmapShape>(canvasEditor)

    override val listChildTools: MutableList<ICanvasTool<*>> = mutableListOf(
        deleteTool,
        measureTool,
        positionTool
    )

    fun checkClickOnTools(x: Int, y: Int):Boolean{
        listChildTools.forEach{
            if(isClickOnObject(
                    it.x,
                    it.y,
                    it.width,
                    it.height,
                    x,
                    y)
            ){
                return true
            }
        }
        return false
    }
    override fun draw(canvas: Canvas) {
        if(objectsForEdit.isNotEmpty()) {
            val desiredPosition = onPositioning(objectsForEdit)
            this.x = desiredPosition.x
            this.y = desiredPosition.y
            val occupiedRect = getRectForOccupiedSpace(objectsForEdit)
            this.width = occupiedRect!!.width
            measureTool.objectsForEdit = objectsForEdit as? MutableList<ICanvasMeasurable> ?: mutableListOf()
            positionTool.objectsForEdit = objectsForEdit as? MutableList<ICanvasMeasurable> ?: mutableListOf()
            deleteTool.objectsForEdit = objectsForEdit

            positionTool.draw(canvas)
            measureTool.draw(canvas)
            deleteTool.draw(canvas)

        }
    }
    private var _objectForEdit: MutableList<BitmapShape> = mutableListOf()
    override var objectsForEdit: MutableList<BitmapShape>
        get() = _objectForEdit
        set(value) {
            if(value.isNotEmpty()) {
                _objectForEdit = value
                positionTool.objectsForEdit = value as MutableList<ICanvasMeasurable>
            }
        }
    override var onPositioning: (List<BitmapShape>) -> Point = {
        val outputPoint: Point = Point(0, 0)
        val occupiedRect = getRectForOccupiedSpace(it)
        if(occupiedRect!= null) {
           outputPoint.also {
               x = occupiedRect.x
               y = occupiedRect.y
           }
        }
        outputPoint
    }

}