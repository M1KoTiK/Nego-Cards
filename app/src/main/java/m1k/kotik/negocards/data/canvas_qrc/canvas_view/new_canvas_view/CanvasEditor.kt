package m1k.kotik.negocards.data.canvas_qrc.canvas_view.new_canvas_view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.*
import m1k.kotik.negocards.data.canvas_qrc.old_govno.DoubleClickChecker

class CanvasEditor (context: Context, attrs: AttributeSet) : CanvasView(context, attrs) {
    //============================Публичные свойства==========================

    //============================Публичные методы=================================
    fun addObject(canvasObject: CanvasObject){
        _objects.add(0,canvasObject)
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

//==============================================================================
//---------------------Обработка нажатий на холсте------------------------------
//==============================================================================
    private val doubleClickChecker = DoubleClickChecker(200) {
        val obj = currentSelectedObject
        if(obj is ICanvasEditable){
            obj.mode = CanvasObjectMode.Edit
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
        val deltaX = event.x - startX
        val deltaY= event.y - startY

        fun editCurrentSelectedObject(){
            val obj = currentSelectedObject ?: return
            if(obj is ICanvasEditable){
                when(obj.mode){
                    CanvasObjectMode.Select->{
                        obj.x =(initialPosX + deltaX / canvasZoom).toInt().
                            coerceIn(MIN_OBJECT_POSX.. canvasWidth - MIN_OBJECT_POSX)

                        obj.y = (initialPosY + deltaY / canvasZoom).toInt().
                        coerceIn(MIN_OBJECT_POSY.. canvasHeight- MIN_OBJECT_POSY)
                    }
                    CanvasObjectMode.Edit->{
                        obj.width = (initialWidth + deltaX.toInt())
                            .coerceIn(MIN_OBJECT_WIDTH..canvasWidth)

                        obj.height = (initialHeight + deltaY.toInt())
                            .coerceIn(MIN_OBJECT_HEIGHT..canvasHeight)
                    }
                }
            }
            invalidate()
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x.toInt()
                startY = event.y.toInt()
                selectObjectBy(startX, startY)
                moveSelectedItemToEndOfList()
                doubleClickChecker.click()
                currentSelectedObject ?: return true
                initialPosX = currentSelectedObject!!.x
                initialPosY = currentSelectedObject!!.y
                initialWidth = currentSelectedObject!!.width
                initialHeight = currentSelectedObject!!.height
            }
            MotionEvent.ACTION_UP -> {

            }
            MotionEvent.ACTION_MOVE -> {
                editCurrentSelectedObject()
            }
        }
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
                setSelectStrokeForSelectedObject(obj, canvas)
                obj.draw(canvas!!)
            }
        }
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