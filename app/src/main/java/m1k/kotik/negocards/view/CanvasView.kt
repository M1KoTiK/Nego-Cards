package m1k.kotik.negocards.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.model.ObjectType
import m1k.kotik.negocards.data.canvas_qrc.model.TextObject

open class CanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint: Paint = Paint()
    private lateinit var mainCanvas : Canvas
    val objects: List<CanvasObject>
        get() = objects_

    protected var objects_: MutableList<CanvasObject> = mutableListOf()

    fun setCanvasObjects(objects: List<CanvasObject>) {
        objects_ = objects.toMutableList()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.apply {
            style = Paint.Style.FILL
            color = Color.WHITE
        }
        val rect = RectF(0f,0f,canvas?.width?.toFloat()!!,canvas?.height?.toFloat()!!)
        canvas?.drawRoundRect(rect,25f,25f,paint)
        for(obj in objects_){
            when(obj.typeObj){
                ObjectType.Text ->{
                   val txObj =  obj as TextObject
                    paint.apply {
                        color = Color.BLACK
                        style = Paint.Style.FILL
                        isAntiAlias = true
                        textSize = txObj.fontSize.toFloat()
                    }
                    canvas?.drawText(txObj.text,txObj.posX.toFloat(),txObj.posY.toFloat(),paint)
                }
                ObjectType.Image->{

                }
                ObjectType.Shape->{

                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(900,600)
    }
}