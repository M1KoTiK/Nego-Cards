package m1k.kotik.negocards.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color.parseColor
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Shader
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.model.TextObject
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectRShape

class ValueStripePicker(context: Context, attrs: AttributeSet) : View(context, attrs) {
    var step: Float = 0.01f
    var startValue: Float = 1f
    var value: Float = startValue
    get() {
        if(stripeValueSequence == StripeValueSequence.Descending){
            val delta = startValue - endValue
            val stepCount = delta / step
            val distanceInStripe = stripeWidth/ stepCount
            val sectionCount = (valuePicker.posX /distanceInStripe).toInt()
            return (startValue - step * sectionCount)
        }
        val delta = endValue - startValue
        val stepCount = delta / step
        val distanceInStripe = stripeWidth/ stepCount
        val sectionCount = (valuePicker.posX /distanceInStripe).toInt()
        return (endValue - step * sectionCount)
    }
    var onValueChange: (value:Float)->Unit = {}
    var endValue: Float = 0f
    private var currentValue:Float = startValue
    private val stripeValueSequence: StripeValueSequence
        get() {
        if (endValue - startValue > 0){
            return StripeValueSequence.Ascending
        }
        return StripeValueSequence.Descending
    }

    //Stripe________________________
    private var stripeHeight = 85
    private var stripeWidth = 600
    private var stripeX = 0
    private var stripeY = 0
    private var stripeObject = RectRShape()
    private var stripeObjectPaint = Paint().also{
        it.color = parseColor("#FFFFFFFF")
        it.shader = LinearGradient(stripeX.toFloat(), 0f,
            stripeWidth.toFloat() + stripeX, 0f,
            0xFFFFFFFF.toInt(),0xFF000000.toInt(),
            Shader.TileMode.REPEAT)
    }
    //--------------



    //Picker_________________________________________________________
    private var valuePicker = RectRShape(
        10,
        10,
        stripeX + OFFSET_VALUE_PICKER_X,
        (stripeY + stripeHeight)/2 - (stripeObject.height - 2 * OFFSET_VALUE_PICKER_Y)/2 ,
        stripeObject.height - 2 * OFFSET_VALUE_PICKER_Y,
        stripeObject.height - 2 * OFFSET_VALUE_PICKER_Y,
        "FF181818",
        CanvasObject.CanvasObjectSerializationTag.Style.Stroke(),
        5)
    //-----------------------------------
    fun setStripeSize(width:Int, height:Int){
        stripeWidth = width
        stripeHeight = height
    }
    private var startX: Int = 0
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.x.toInt()
        val deltaX = x - startX
        when(event!!.action){
            MotionEvent.ACTION_MOVE ->{
                if(x >= stripeX && x <= this.width ) {
                    ValueStripePicker
                    valuePicker.posX =
                        startX + deltaX - OFFSET_VALUE_PICKER_X - valuePicker.width / 2
                }
            }
            MotionEvent.ACTION_DOWN->{
                if(x >= stripeX  && x <= this.width ) {
                    valuePicker.posX = x - OFFSET_VALUE_PICKER_X - valuePicker.width / 2
                    startX = event!!.x.toInt()
                }
            }
            MotionEvent.ACTION_UP->{
            }
        }
        onValueChange.invoke(value)
        this.invalidate()
        return true
    }



    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)
        //drawStripe
        stripeObject.also {
            it.posX = stripeX
            it.posY = stripeY
            it.height = stripeHeight
            it.width = stripeWidth
        }
        stripeObject.drawWithCustomPaint(canvas!!, stripeObjectPaint)

        //drawPicker'
        valuePicker.color = "FF181818"
        valuePicker.style = CanvasObject.CanvasObjectSerializationTag.Style.Stroke()
        valuePicker.draw(canvas)
        valuePicker.color = "40303030"
        valuePicker.style = CanvasObject.CanvasObjectSerializationTag.Style.Fill()
        valuePicker.draw(canvas)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(stripeWidth + OFFSET_VALUE_PICKER_X, stripeHeight)
    }
    companion object{
        enum class StripeValueSequence {
            Ascending,
            Descending
        }
        const val OFFSET_VALUE_PICKER_Y = 14
        const val OFFSET_VALUE_PICKER_X = 50
    }
}
