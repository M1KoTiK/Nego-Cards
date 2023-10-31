package m1k.kotik.negocards.data.canvas_qrc.canvas_view.new_canvas_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.toColorInt
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasDrawable
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasZoomable
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Rectangle
import m1k.kotik.negocards.data.canvas_qrc.old_govno.canvas_object_types.CanvasObjectType

/**
 * Представляет холст на котором выводятся объекты без возможности их изменять
 * но есть возможность выполнять действия при их наличии закрепленные за объектом
 */
open class CanvasView (context: Context, attrs: AttributeSet) : View(context, attrs) {
    //============================Публичные свойства==========================
    // Размеры холста
    var canvasHeight: Int = 600
    set(value) {
        field = value
        this.requestLayout()
    }
    var canvasWidth: Int = 900
        set(value) {
            field = value
            this.requestLayout()
        }
    /**Масштаб (в процентах)*/
    var canvasZoom: Float = 1f
        set(value) {
            field = value
            this.requestLayout()
        }
    /** Выбранный на канвасе объект считается как первый в списке, например когда на одном и том же
        месте находятся сразу несколько объектов выбираться будет самый верхний */
    val currentSelectedObject: CanvasObject?
        get() {
            if (_listCurrentSelectedObjects.isNotEmpty()) {
                return _listCurrentSelectedObjects[0]
            }
            return null
        }
    /**Список выбранных объектов*/
    val listCurrentSelectedObjects: MutableList<CanvasObject>
        get() {
                return _listCurrentSelectedObjects
        }

    /** Коллбэк который срабатывает когда меняется текущий выбранный объект
        (он сработает даже если будет заново выбран тот объект который и так выбран) */
    var onCurrentSelectedObjectChange: () -> Unit = {}

    val objects: List<CanvasObject>
        get() = _objects

//============================Публичные методы=================================
    fun selectObjectBy(x: Int, y: Int) {
    listCurrentSelectedObjects.clear()
        for (obj in _objects.reversed()) {
            if (isCursorHoveredOver(x,y,obj)) {
                listCurrentSelectedObjects.add(obj)
            }
        }
    }

    fun clearObject() {
        _objects.clear()
        invalidate()
    }

    fun setObjects(listObjects: List<CanvasObject>){
        for(obj in listObjects){
            _objects.add(obj)
        }
        invalidate()
    }

//============================Приватные свойства================================
    private var _objects: MutableList<CanvasObject> = mutableListOf()
    private var _listCurrentSelectedObjects: MutableList<CanvasObject> = mutableListOf()

//=============================Приватные методы=================================

    private fun isCursorHoveredOver(x:Int, y:Int, obj: CanvasObject, touchZoneExpansion: Int = 10)
    : Boolean{
        if(x >= obj.x - touchZoneExpansion && x <= obj.x + obj.width + touchZoneExpansion &&
           y >= obj.y - touchZoneExpansion && y <= obj.y + obj.height + touchZoneExpansion ){
            return true
        }
        return false
    }
    fun setZoomForObject(obj: CanvasObject):Boolean{
        if(obj is ICanvasZoomable){
            obj.zoomValue = canvasZoom
            return true
        }
        return false
    }
//==============================================================================
//---------------------Обработка нажатий на холсте------------------------------
//==============================================================================
    private var startX: Int = 0
    private var startY: Int = 0
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return true
        val x = event.x.toInt()
        val y = event.y.toInt()
        val deltaX = x - startX
        val deltaY = y - startY
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = x
                startY = y
                selectObjectBy(startX, startY)
            }
            MotionEvent.ACTION_UP -> {

            }
            MotionEvent.ACTION_MOVE -> {

            }
        }
        return true
    }
    //Это здесь только для теста потом его надо будет убрать!!!
    var background = Rectangle(0,0,900,600, Paint().also { it.color= Color.WHITE })
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background.draw(canvas!!)
        for(obj in _objects){
            if(obj is ICanvasDrawable){
                setZoomForObject(obj)
                obj.draw(canvas!!)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(canvasWidth, canvasHeight)
    }
}