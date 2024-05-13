package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_editors

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.*
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.BitmapShape
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.canvas_tools.BitmapShapeModifyTool
import m1k.kotik.negocards.data.canvas_qrc.old_govno.DoubleClickChecker

class CanvasEditor (context: Context, attrs: AttributeSet) : CanvasView(context, attrs) {
    //============================Публичные свойства==========================

    //============================Публичные методы=================================
    fun addObject(canvasObject: Any){
        _objects.add(0,canvasObject)
        listCurrentSelectedObjects.add(canvasObject)
    }
    fun deleteObject(canvasObject: Any){
        _objects.remove(canvasObject)
    }
    //========================Приватные/protected свойства==========================

    //=============================Приватные методы=================================
    private fun moveSelectedItemToEndOfList() {
        val obj = currentSelectedObject ?: return
        if (_objects.contains(obj)) {
            _objects.remove(obj)
            _objects.add(obj)
        }
    }
    val bitmapShapeModifyTool = BitmapShapeModifyTool(this)
//==============================================================================
//---------------------Обработка нажатий на холсте------------------------------
//==============================================================================
    private val doubleClickChecker = DoubleClickChecker(200) {
        val obj = currentSelectedObject
        if(obj is ICanvasSelectable){
            obj.isSelected = true
        }
    }

    private var startX: Int = 0
    private var startY: Int = 0
    private var initialPosX = 0
    private var initialPosY = 0
    private var initialWidth = 0
    private var initialHeight = 0
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return true
//        val deltaX = event.x - startX
//        val deltaY= event.y - startY
//        fun editCurrentSelectedObject(){
//            val obj = currentSelectedObject ?: return
//            if(obj is ICanvasSelectable){
//                if(obj.isSelected){
//
//                    if(currentSelectedObject is ICanvasMeasurable){
//
//                    }
//                }
//            }
//        invalidate()
//        }
//
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> {
//                startX = event.x.toInt()
//                startY = event.y.toInt()
//                selectObjectBy(startX, startY)
////                moveSelectedItemToEndOfList()
////                doubleClickChecker.click()
////                currentSelectedObject ?: return true
////                initialPosX = currentSelectedObject!!.x
////                initialPosY = currentSelectedObject!!.y
////                initialWidth = currentSelectedObject!!.width
////                initialHeight = currentSelectedObject!!.height
//            }
//            MotionEvent.ACTION_UP -> {
//
//            }
//            MotionEvent.ACTION_MOVE -> {
////                editCurrentSelectedObject()
//            }
//
//        }
        bitmapShapeModifyTool.objectsForEdit = listCurrentSelectedObjects as? List<BitmapShape> ?: listOf()
        bitmapShapeModifyTool.onTouchEvent(event)
        invalidate()
        return true
    }
//==============================================================================
//-------------------------Отрисовка на холсте----------------------------------
//==============================================================================

    override fun onDraw(canvas: Canvas?) {
        _backgroundObject!!.draw(canvas!!)
        for(obj in _objects){
            if(obj is ICanvasDrawable){
                setZoomForObject(obj)
                obj.draw(canvas!!)
            }
        }
        bitmapShapeModifyTool.draw(canvas)
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(canvasWidth, canvasHeight)
    }
    companion object{
        const val MIN_OBJECT_POSX = 5
        const val MIN_OBJECT_POSY = 5
        const val MIN_OBJECT_HEIGHT = 10
        const val MIN_OBJECT_WIDTH = 10
    }
}