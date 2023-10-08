package m1k.kotik.negocards.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.ShapeObject
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.shapes.RectRShape

abstract class Slider (context: Context, attrs: AttributeSet) : View(context, attrs) {
    //OutputValues___________________________________________________________________________________
    var outValue: Float = 0F
        get() {
            var minValue = (stripeObject.posX + OFFSET_POINTER + SPARE_SPACE ).toFloat()
            var maxValue: Float  = (stripeObject.posX + stripeObject.width - pointerObject.width - OFFSET_POINTER - SPARE_SPACE).toFloat()
            if(sliderValueSequence == SliderValueSequence.Descending){
                maxValue = (stripeObject.posX + OFFSET_POINTER + SPARE_SPACE ).toFloat()
                minValue = (stripeObject.posX + stripeObject.width - pointerObject.width - OFFSET_POINTER - SPARE_SPACE).toFloat()
            }
            val currentValue: Float = (pointerObject.posX ).toFloat()
            val progressFraction: Float = (currentValue - minValue)/(maxValue - minValue)
            val halfStepSize :Float = step / 2f
            val progressFractionStepped: Float = progressFraction - (progressFraction - halfStepSize) % step + halfStepSize
            field = progressFractionStepped
            when(sliderValueSequence){
                SliderValueSequence.Ascending->{
                    if(field >= endSliderValue){
                        field = endSliderValue
                    }
                    else if(field <= startSliderValue){
                        field = startSliderValue
                    }
                }
                SliderValueSequence.Descending->{
                    if(field <= endSliderValue){
                        field = endSliderValue
                    }
                    else if(field >= startSliderValue){
                        field = startSliderValue
                    }
                }
                SliderValueSequence.Equals->{
                    field = 0F
                }
            }

            println("$field")
            return field
        }
    private set
    //---------------------------------------------------------------------------------------------------

    //ValuesForSetup_____________________________
    private var step: Float = 0f
    private var startSliderValue: Float = 1f
    private var endSliderValue: Float = 0f
    //--------------------------------

    open fun onAfterSetup(){}

    //Callback________________________________________
    var onSliderValueChange: (Float)->Unit = {}
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
    var pointerObject : ShapeObject = RectRShape().also {
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
        onSliderValueChange.invoke(outValue)
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
        const val SPARE_SPACE = 10
    }

}