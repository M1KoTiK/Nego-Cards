package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_editors

import android.content.Context
import android.graphics.Canvas
import android.graphics.Insets
import android.util.AttributeSet
import android.view.MotionEvent
import m1k.kotik.negocards.data.canvas_qrc.canvas_serialization.CanvasSerialization
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.*
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.BitmapShape
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.canvas_tools.BitmapShapeModifyTool
import m1k.kotik.negocards.data.canvas_qrc.old_govno.DoubleClickChecker
import m1k.kotik.negocards.data.measure_utils.isClickOnObject
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import java.util.Stack

class CanvasEditor (context: Context, attrs: AttributeSet) : CanvasView(context, attrs) {
    //============================Публичные свойства==========================

    //============================Публичные методы=================================
    fun backAction(){
        listCurrentSelectedObjects.clear()
        bitmapShapeModifyTool.objectsForEdit.clear()
        invalidate()
        if(!backstack.isNullOrEmpty()){
            val CObjects = CanvasSerialization.deserializeCanvas<ISerializationObject>(backstack.last())
            backstack.removeLast()
            if (!CObjects.isNullOrEmpty()){
                _objects = CObjects.drop(1).toMutableList()
                this.invalidate()
            }
        }

    }
    fun addObject(canvasObject: Any){
        _objects.add(0,canvasObject)
        addToBackStack()
    }
    fun deleteObject(canvasObject: Any){
        addToBackStack()
        _objects.remove(canvasObject)
    }
    fun clearSelected(){
        listCurrentSelectedObjects.clear()
    }
    fun serialize(): String?{
        val objectList  = mutableListOf<ISerializationObject>()
       objectList.add(_backgroundObject)
        _objects.forEach{
            if(it is ISerializationObject){
                objectList.add(it)
            }
        }
        return CanvasSerialization.serializeCanvas(objectList)
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
    private fun addToBackStack(){
        val ser = serialize()
        if (ser != null) {
            if(backstack.isNotEmpty()){
                if(backstack.last()!= ser){
                    backstack.add(ser)
                    forwardstack.clear()
                }
            }
            else {
                backstack.add(ser)
                forwardstack.clear()
            }
        }
    }
    val bitmapShapeModifyTool = BitmapShapeModifyTool(this)
//==============================================================================
//---------------------Обработка нажатий на холсте------------------------------
//==============================================================================
    private val doubleClickChecker = DoubleClickChecker(200) {
        val obj = currentSelectedObject
    }
    private val backstack = mutableListOf<String>()
    private val forwardstack: Stack<String> = Stack()
    private var startX: Int = 0
    private var startY: Int = 0
    private var initialPosX = 0
    private var initialPosY = 0
    private var initialWidth = 0
    private var initialHeight = 0
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return true
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if(!bitmapShapeModifyTool.checkClickOnTools(event.x.toInt(), event.y.toInt())) {
                    listCurrentSelectedObjects.clear()
                    _objects.forEach {
                        if (it is ICanvasMeasurable) {
                            if (isClickOnObject(
                                    it.x,
                                    it.y,
                                    it.width,
                                    it.height,
                                    event.x.toInt(),
                                    event.y.toInt()
                                )
                            ) {
                                listCurrentSelectedObjects.add(it)
                            }
                        }
                    }
                }
                else if (listCurrentSelectedObjects.isEmpty()){
                    _objects.forEach {
                        if (it is ICanvasMeasurable) {
                            if (isClickOnObject(
                                    it.x,
                                    it.y,
                                    it.width,
                                    it.height,
                                    event.x.toInt(),
                                    event.y.toInt()
                                )
                            ) {
                                listCurrentSelectedObjects.add(it)
                            }
                        }
                    }
                }

            }
            MotionEvent.ACTION_UP -> {
                addToBackStack()
            }
            MotionEvent.ACTION_MOVE -> {
            }

        }


        if(currentSelectedObject != null) {
            bitmapShapeModifyTool.objectsForEdit = mutableListOf(currentSelectedObject!!)
            invalidate()
        }
        else{
            bitmapShapeModifyTool.objectsForEdit.clear()
        }
            //listCurrentSelectedObjects as? MutableList<BitmapShape> ?: mutableListOf()
        bitmapShapeModifyTool.onTouchEvent(event)

        invalidate()
        return true
    }
//==============================================================================
//-------------------------Отрисовка на холсте----------------------------------
//==============================================================================

    override fun onDraw(canvas: Canvas?) {
        println(serialize())
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