package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_editors

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.toColorInt
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.*
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.BitmapShape
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Rectangle
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.canvas_tools.CanvasPositionEditTool

/**
 * Представляет холст на котором выводятся объекты без возможности их изменять
 * но c возможностью выполнять действия при их наличии закрепленные за объектом
 */
open class CanvasView (context: Context, attrs: AttributeSet) : View(context, attrs) {
    //============================Публичные свойства==========================
    // Размеры холста
    var canvasHeight: Int = 600
    set(value) {
        field = value
        requestLayout()
    }
    var canvasWidth: Int = 900
        set(value) {
            field = value
            requestLayout()
        }
    /**Масштаб (в процентах)*/
    var canvasZoom: Float = 1f
        set(value) {
            field = value
            requestLayout()
        }
    /** Выбранный на канвасе объект считается как первый в списке, например когда на одном и том же
        месте находятся сразу несколько объектов выбираться будет самый верхний */
    val currentSelectedObject: Any?
        get() {
            if (_listCurrentSelectedObjects.isNotEmpty()) {
                return _listCurrentSelectedObjects[0]
            }
            return null
        }

    /**Список выбранных объектов*/
    val listCurrentSelectedObjects: MutableList<Any>
        get() {
                return _listCurrentSelectedObjects
        }

    /** Коллбэк который срабатывает когда меняется текущий выбранный объект
        (он сработает даже если будет заново выбран тот объект который и так выбран) */
    var onCurrentSelectedObjectChange: () -> Unit = {}

    val objects: List<Any>
        get() = _objects

    var colorForSelectedObjectStroke = defaultColorForSelectedObjectStroke
//============================Публичные методы=================================
    fun selectObjectBy(x: Int, y: Int) {
    clearCanvasModeForObjects()
    listCurrentSelectedObjects.clear()
        for (obj in _objects.reversed()) {
            if (isCursorHoveredOver(x,y,obj)) {
                listCurrentSelectedObjects.add(obj)
            }
            if(listCurrentSelectedObjects.isNotEmpty()) {
//              var upperSelectedObj = listCurrentSelectedObjects[0]
                onCurrentSelectedObjectChange.invoke()
            }
        }
    invalidate()
    }
    init {

    }
    fun clearObject() {
        _objects.clear()
        invalidate()
    }

    fun setBackgroundObject(backgroundObject: BitmapShape){
        setCanvasSize(backgroundObject.width,backgroundObject.height)
        _backgroundObject = backgroundObject
        invalidate()
    }

    /**
     * Удаляет все старые объекты и помещает новые на канвас
     */
    fun setObjects(listObjects: List<Any>){
        _objects.clear()
        for(obj in listObjects){
            _objects.add(obj)
        }
        invalidate()
    }

//========================Приватные/protected свойства==========================
    private val defaultBackgroundObject = Rectangle(0,0,canvasWidth,canvasHeight,
    Paint().also { it.color = Color.WHITE })

    protected var _objects: MutableList<Any> = mutableListOf()
    protected var _backgroundObject: BitmapShape = defaultBackgroundObject
    private var _listCurrentSelectedObjects: MutableList<Any> = mutableListOf()
//=============================Приватные методы=================================

    private fun isCursorHoveredOver(x:Int, y:Int, obj: Any, touchZoneExpansion:Float = 2f)
    : Boolean{
        var zoomValue = 1f
        if(obj is ICanvasZoomable){
            zoomValue = obj.zoomValue
        }
        if (obj is ICanvasMeasurable) {
            if (x >= obj.x * zoomValue - touchZoneExpansion && x <= (obj.x + obj.width) * zoomValue + touchZoneExpansion &&
                y >= obj.y * zoomValue - touchZoneExpansion && y <= (obj.y + obj.height) * zoomValue + touchZoneExpansion
            ) {
                return true
            }
        }
            return false

    }
    private fun clearCanvasModeForObjects(){
        for(obj in _objects){

        }
    }
    private fun setCanvasSize(width: Int, height: Int) {
        canvasWidth = width
        canvasHeight = height
        requestLayout()
    }
    fun setZoomForObject(obj: Any):Boolean{
        if(obj is ICanvasZoomable){
//            obj.zoomValue = canvasZoom
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
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = x
                startY = y
                selectObjectBy(startX, startY)
            }
        }

        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        _backgroundObject.draw(canvas!!)
        for(obj in _objects){
            if(obj is ICanvasDrawable){
//                setZoomForObject(obj)
                obj.draw(canvas!!)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(canvasWidth, canvasHeight)
    }
    companion object{
        val defaultColorForSelectedObjectStroke = "#905954E1".toColorInt()
    }
}