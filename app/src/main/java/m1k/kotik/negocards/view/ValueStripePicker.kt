package m1k.kotik.negocards.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color.parseColor
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.OvalShape
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectRShape

class ValueStripePicker(context: Context, attrs: AttributeSet) : View(context, attrs) {
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_MOVE ->{
                x = event.x.toInt()
                y = event.y.toInt()
                this.invalidate()
            }
            MotionEvent.ACTION_DOWN->{
                x = event.x.toInt()
                y = event.y.toInt()
                this.invalidate()

            }
            MotionEvent.ACTION_UP->{

            }

        }
        return true
    }
    private var x:Int? = null
    private var y:Int? = null
    private var stripeHeight = 600
    private var stripeWidth = 600
    private var stripeX = 0
    private var stripeY = 0
    fun setStripeSize(width:Int, height:Int){
        stripeWidth = width
        stripeHeight = height
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
       RectRShape().drawRectRWithCustomPaint(canvas!!, Paint().also {
           it.style = Paint.Style.STROKE
           it.color = parseColor("#FF181818")

       } )
        if(x!= null && y!=null){
            var oval = OvalShape(x!!,y!!,65,65,"FF181818",
                CanvasObject.CanvasObjectSerializationTag.Style.Stroke(),5)
            oval.draw(canvas!!)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(600,600)
    }
}