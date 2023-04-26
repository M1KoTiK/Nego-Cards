package m1k.kotik.negocards.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectRShape

abstract class CustomSlider (context: Context, attrs: AttributeSet) : View(context, attrs) {
    //OutputValues_____________________
    var outValue: Float = 0f
    //-------------------

    //ValuesForSetup_____________________________
    private var step: Float = 0f
    private var startSliderValue: Float = 0f
    private var endSliderValue: Float = 0f
    //--------------------------------

    abstract fun onBeforeSetup()
    abstract fun onAfterSetup()

    //Callbacks________________________________________
    private var onSliderValueChange: ()->Unit = {}
    fun setOnSliderValueChange(Action:()->Unit){
        onSliderValueChange = Action
    }
    //-------------------------

    //Stripe__________________________________
    var stripeObject = RectRShape()
    var stripePaint = Paint()
    var stripeWidth = 600
    var stripeHeight = 100

    //Pointer__________________________________
    private val offsetPointerSize = 16
    var pointerObject = RectRShape()
    var pointerPaint = Paint()
    private var pointerWidth = stripeHeight - offsetPointerSize
    private var pointerHeight = stripeHeight - offsetPointerSize
    fun setPointerSize(w:Int, h:Int){
        pointerHeight = h
        pointerWidth = w
    }
    //------------------------------

    fun setup(step:Float, startSliderValue:Float,endSliderValue: Float){
        onBeforeSetup()
        this.step = step
        this.startSliderValue = startSliderValue
        this.endSliderValue = endSliderValue
        onAfterSetup()

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}