package m1k.kotik.negocards.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import m1k.kotik.negocards.data.canvas_qrc.model.ShapeObject
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectRShape
import java.math.RoundingMode
import kotlin.math.abs

abstract class Slider (context: Context, attrs: AttributeSet) : View(context, attrs) {
    //OutputValues___________________________________________________________________________________
    var outValue: Float = 0F
        get() {
            try {
                var deltaSliderValue = abs(startSliderValue) + abs(endSliderValue)
                val stepCount = deltaSliderValue / step
                val deltaStripeDistance = stripeObject.posX + stripeObject.width - 2 * OFFSET_POINTER - pointerObject.height
                val distanceInStripe = deltaStripeDistance / stepCount
                val sectionCount = ((pointerObject.posX - OFFSET_POINTER) / distanceInStripe ).toInt()
                if(startSliderValue< 0 && endSliderValue<0){
                    deltaSliderValue = abs(abs(startSliderValue) - abs(endSliderValue))
                }
                if (sliderValueSequence == SliderValueSequence.Descending) {
                    field = (startSliderValue - step * sectionCount)
                }
                else if(sliderValueSequence == SliderValueSequence.Ascending) {
                    field =  (startSliderValue + step * sectionCount)
                }
            }
            catch (e:Exception){
                Toast.makeText(context, "Слайдер не был инициализирован," +
                        " либо введены некорректные данный",Toast.LENGTH_LONG).show()
                field = 0f
            }
            field = field.toBigDecimal().setScale(5, RoundingMode.DOWN).toFloat()
            return field
        }
    private set
    //---------------------------------------------------------------------------------------------------

    //ValuesForSetup_____________________________
    private var step: Float = 0f
    private var startSliderValue: Float = 0f
    private var endSliderValue: Float = 0f
    //--------------------------------

    open fun onAfterSetup(){}

    //Callbacks________________________________________
    private var onSliderValueChange: ()->Unit = {}
    fun setOnSliderValueChange(Action:()->Unit){
        onSliderValueChange = Action
    }
    //-------------------------

    //Stripe__________________________________
    var stripeObject: ShapeObject = RectRShape().also {
        it.posX = 0
        it.posY = 0
    }
    var stripePaint: Paint= Paint().also {
        it.style = Style.FILL
        it.color = 0xDDEEEEEE.toInt()
    }
    var stripeWidth = 600
    var stripeHeight = 85

    //Pointer__________________________________
    var pointerObject :ShapeObject = RectRShape().also {
        it.posX = OFFSET_POINTER
        it.leftCorner = 12
        it.rightCorner = 12
    }
    var pointerFirstPaint: Paint = Paint().also {
        it.strokeWidth = 5f
        it.style = Style.STROKE
        it.color = 0xFF181818.toInt()
    }
    var pointerSecondPaint: Paint = Paint().also {
        it.style = Style.FILL
        it.color = 0x40909090
    }
    private var pointerWidth = stripeHeight - 2 * OFFSET_POINTER
    private var pointerHeight = stripeHeight - 2 * OFFSET_POINTER

    fun setPointerSize(w:Int, h:Int){
        pointerHeight = h
        pointerWidth = w
    }

    //------------------------------
    private val sliderValueSequence: SliderValueSequence
        get() {
            if (endSliderValue > startSliderValue){
                return SliderValueSequence.Ascending
            }
            else if (endSliderValue < startSliderValue) {
                return SliderValueSequence.Descending
            }
            return SliderValueSequence.Equals
        }

    open fun setup(step:Float, startSliderValue:Float,endSliderValue: Float){
        this.step = step
        this.startSliderValue = startSliderValue
        this.endSliderValue = endSliderValue
        onAfterSetup()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //drawStripeObject_______________________________________
        stripeObject.also {
            it.width = stripeWidth
            it.height = stripeHeight
        }
        stripeObject.drawWithCustomPaint(canvas!!, stripePaint)
        //----------------------------------------

        //drawPointerObject____________________________________
        pointerObject.also {
            it.width = pointerWidth
            it.height = pointerHeight
            it.posY = (stripeObject.posY + stripeHeight)/2 - (stripeObject.height - 2 * OFFSET_POINTER)/2
        }
        pointerObject.drawWithCustomPaint(canvas!!, pointerFirstPaint)
        pointerObject.drawWithCustomPaint(canvas!!, pointerSecondPaint)
    }
    private var startX = 0
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.x
        val deltaX = x - startX
        val downBound:Float =
            (stripeObject.posX + OFFSET_POINTER/2 + pointerObject.width/2).toFloat()
        val upBound: Float =
            (stripeObject.posX + stripeObject.width - OFFSET_POINTER - pointerObject.width/2).toFloat()
        when(event.action){
            MotionEvent.ACTION_MOVE -> {
                if(x in downBound..upBound)
                {
                    pointerObject.posX = (startX + deltaX - OFFSET_POINTER - pointerObject.width / 4).toInt()
                }
            }
            MotionEvent.ACTION_DOWN->{
                if(x in downBound..upBound) {
                    pointerObject.posX = (x - OFFSET_POINTER - pointerObject.width / 4).toInt()
                    startX = event.x.toInt()
                }
            }
            MotionEvent.ACTION_UP->{
            }
        }
        println("$outValue")
        this.invalidate()
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(stripeWidth, stripeHeight)
    }
    companion object{
        private enum class SliderValueSequence {
            Equals,
            Ascending,
            Descending
        }
        const val OFFSET_POINTER = 12
    }

}