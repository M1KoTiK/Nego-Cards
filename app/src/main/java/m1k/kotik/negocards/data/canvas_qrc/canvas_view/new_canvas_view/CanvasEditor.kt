package m1k.kotik.negocards.data.canvas_qrc.canvas_view.new_canvas_view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.CanvasEditor
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.*
import kotlin.math.max

class CanvasEditor (context: Context, attrs: AttributeSet) : CanvasView(context, attrs) {
    private var startX: Int = 0
    private var startY: Int = 0
    var initialPosX = 0
    var initialPosY = 0
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return true
        val x = event.x.toInt()
        val y = event.y.toInt()
        val deltaX = x - startX
        val deltaY = y - startY
        fun editCurrentSelectedObject(){
            val obj = currentSelectedObject ?: return
            if(obj is ICanvasEditable){
                when(obj.mode){
                    CanvasObjectMode.Select->{
                        println("initPosX $initialPosX")
                        println("deltaX $deltaX")
                        obj.x = max(initialPosX + deltaX / canvasZoom, MIN_OBJECT_POSX).toInt()
                        obj.y = max(initialPosY + deltaY / canvasZoom, MIN_OBJECT_POSY).toInt()
                    }
                    CanvasObjectMode.Edit->{

                    }
                }
            }
            invalidate()
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = x
                startY = y
                selectObjectBy(startX, startY)
                currentSelectedObject ?: return true
                initialPosX = currentSelectedObject!!.x
                initialPosY = currentSelectedObject!!.y
            }
            MotionEvent.ACTION_UP -> {

            }
            MotionEvent.ACTION_MOVE -> {
                editCurrentSelectedObject()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(canvasWidth, canvasHeight)
    }
    companion object{
        const val MIN_OBJECT_POSX = 5f
        const val MIN_OBJECT_POSY = 5f
    }
}