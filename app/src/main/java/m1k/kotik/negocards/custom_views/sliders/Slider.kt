package m1k.kotik.negocards.custom_views.sliders

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.toColorInt
import m1k.kotik.negocards.R

class Slider (context: Context, attrs: AttributeSet) : View(context, attrs) {
    var startValue = 0f
    set(value) {
        field = value
        invalidate()
    }
    var endValue = 2f
        set(value) {
            field = value
            invalidate()
        }
    var step = 0.01f
        set(value) {
            field = value
            invalidate()
        }
    val outputValue: Float
        get(){
            val currentValueInPercent: Float = localCurrentValue/localDistance
            val currentValue = currentValueInPercent * (endValue - startValue) + startValue
            val steppedValue = currentValue - ((currentValue - step) % step)
            val roundedValue = ("%.4f".format(Math.round(steppedValue/ step) * step)).toFloat()
            if(currentValueInPercent == 1f){
                return endValue
            }
            if(currentValueInPercent == 0f){
                return startValue
            }
            return roundedValue
        }
    var onSliderChangeValue : (Float)->Unit = {}

//====================================
//Свойства задней панели (Stripe)
//====================================
    private var stripeHeight = 65f
    set(value){
        field = value
        invalidate()
        requestLayout()
    }
    private var stripeColor = Color.WHITE
        set(value){
            field = value
            invalidate()
        }
    private var stripeStrokeColor = Color.BLACK
        set(value){
            field = value
            invalidate()
        }
    private var stripeWidth:Float = 200f
        set(value){
            field = value
            invalidate()
            requestLayout()
        }
    private var stripeRCorner = 10f
        set(value) {
            field = value
            invalidate()
        }
    private var stripeLCorner = 10f
        set(value) {
            field = value
            invalidate()
        }
    private var stripeX:Float = 0f
    get() = width/2 - stripeWidth/2

    private var stripeY: Float = 0f
        get() = height.toFloat()/2 - stripeHeight.toFloat()/2

    private var isStripeStrokeEnable: Boolean = false
        set(value) {
            field = value
            invalidate()
        }
    private var stripeStrokeWidth = 4f
        set(value) {
            field = value
            invalidate()
        }

//====================================
//Свойства курсора (Cursor)
//====================================
    private var cursorHeight = 100f
        set(value) {
            field = value
            invalidate()
        }
    private var cursorWidth = 100f
        set(value) {
            field = value
            invalidate()
        }
    private var defaultCursorX: Float = stripeX
    get() {
        return stripeX
    }
    private var cursorX: Float? = null
        set(value) {
            if (value != null) {
                val currentForSet = value - localStartValue
                if(currentForSet <0){
                    field = defaultCursorX
                }
                else if (currentForSet > localDistance) {
                    field = localEndValue
                }
                else {
                    field = value
                }
                invalidate()
                requestLayout()
            }
        }

    private var cursorY: Float = 0f
        get() {
            return (stripeY + stripeHeight) - stripeHeight/2 - cursorHeight/2
        }
        set(value){
            field = value
            invalidate()
            requestLayout()
        }
    private var cursorLCorner = 10f
        set(value) {
            field = value
            invalidate()
        }
    private var cursorRCorner = 10f
        set(value) {
            field = value
            invalidate()
        }
    private var cursorColor = "#D9D9D9".toColorInt()
        set(value) {
            field = value
            invalidate()
        }
    private var cursorStrokeColor = Color.BLACK
        set(value) {
            field = value
            invalidate()
        }
    private var isCursorStrokeEnable: Boolean = false
        set(value) {
            field = value
            invalidate()
        }
    private var cursorStrokeWidth = 4f
        set(value) {
            field = value
            invalidate()
        }
    private var cursorXOffset: Float = 20f
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }
    //==================================================
    // Свойства для Caption
    //==================================================
    private var captionColor: Float = 20f
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }
    //=================================================
    private var localStartValue: Float
        get() = defaultCursorX

    private var localEndValue: Float
        get()= localStartValue + stripeWidth - cursorWidth

    private val localDistance: Float
        get()= localEndValue.minus(localStartValue)

    private val localCurrentValue: Float
        get() {
            if(cursorX != null) {
                val current = cursorX!! - localStartValue
                if(current <=0){
                    return 0f
                }
                if(current >= localDistance){
                    return localDistance
                }
                return current
            }
            return 0f
        }
    //===================================================
    init {
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.Slider)
        stripeHeight = typedArray.getDimension(R.styleable.Slider_stripeHeight,85f)
        stripeWidth = typedArray.getDimension(R.styleable.Slider_stripeWidth,600f)
        stripeColor = typedArray.getColor(R.styleable.Slider_stripeColor, Color.WHITE)
        stripeRCorner = typedArray.getDimension(R.styleable.Slider_stripeRCorner,25f)
        stripeLCorner = typedArray.getDimension(R.styleable.Slider_stripeLCorner,25f)
        cursorHeight = typedArray.getDimension(R.styleable.Slider_cursorHeight,70f)
        cursorWidth = typedArray.getDimension(R.styleable.Slider_cursorWidth,70f)
        cursorColor = typedArray.getColor(R.styleable.Slider_cursorColor,"#D9D9D9".toColorInt())
        cursorRCorner = typedArray.getDimension(R.styleable.Slider_cursorRCorner,15f)
        cursorLCorner = typedArray.getDimension(R.styleable.Slider_cursorLCorner,15f)
        cursorStrokeColor = typedArray.getColor(R.styleable.Slider_cursorStrokeColor,Color.BLACK)
        stripeStrokeColor= typedArray.getColor(R.styleable.Slider_stripeStrokeColor,Color.BLACK)
        isCursorStrokeEnable =typedArray.getBoolean(R.styleable.Slider_isCursorStroke,true)
        isStripeStrokeEnable =typedArray.getBoolean(R.styleable.Slider_isStripeStroke,false)
        cursorStrokeWidth = typedArray.getDimension(R.styleable.Slider_cursorStrokeWidth,5f)
        stripeStrokeWidth = typedArray.getDimension(R.styleable.Slider_stripeStrokeWidth,5f)
        cursorXOffset = 0f
        typedArray.recycle()
        defaultCursorX = stripeX
        localStartValue = defaultCursorX + cursorXOffset
        localEndValue= defaultCursorX + stripeWidth - 2 * cursorXOffset - cursorWidth

        invalidate()
        requestLayout()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawStripe(canvas)
        drawCursor(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return true
        var x = event.x
        var y = event.y
        cursorX = x - cursorWidth/2
        onSliderChangeValue.invoke(outputValue)
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = stripeWidth.toInt() + stripeStrokeWidth.toInt() + 2*cursorXOffset.toInt()
        var additionHeight = 0f
        if(cursorHeight > stripeHeight){
            additionHeight = cursorHeight - stripeHeight
        }
        val desiredHeight = stripeHeight.toInt() + stripeStrokeWidth.toInt() + additionHeight.toInt()
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width: Int = if (widthMode == MeasureSpec.EXACTLY) {
            widthSize
        } else if (widthMode == MeasureSpec.AT_MOST) {
            Math.min(desiredWidth, widthSize)
        } else {
            desiredWidth
        }

        val height: Int = if (heightMode == MeasureSpec.EXACTLY) {
            heightSize
        } else if (heightMode == MeasureSpec.AT_MOST) {
            Math.min(desiredHeight, heightSize)
        } else {
            desiredHeight
        }
        setMeasuredDimension(width, height)
    }
    private fun drawStripe(canvas: Canvas?){
        canvas?.drawRoundRect(
            stripeX.toFloat(),
            stripeY,
            stripeWidth.toFloat() + stripeX + 2 * cursorXOffset,
            stripeHeight.toFloat() + stripeY,
            stripeRCorner.toFloat(),stripeLCorner.toFloat(),
            Paint().also {
                it.isAntiAlias = true
                it.color = stripeColor
                it.style = Style.FILL
            }
        )
        if(isStripeStrokeEnable) {
            canvas?.drawRoundRect(
                stripeX.toFloat(),
                stripeY,
                stripeWidth.toFloat() + stripeX + 2 * cursorXOffset,
                stripeHeight.toFloat() + stripeY,
                stripeRCorner.toFloat(),
                stripeLCorner.toFloat(),
                Paint().also {
                    it.isDither = true
                    it.isAntiAlias = true
                    it.color = stripeStrokeColor
                    it.style = Style.STROKE
                    it.strokeWidth = stripeStrokeWidth
                })
        }
    }
    private fun drawCursor(canvas: Canvas?){
        var localCursorX= defaultCursorX
        if(cursorX != null){
            localCursorX = cursorX!!
        }
        canvas?.drawRoundRect(
            localCursorX + cursorXOffset,
            cursorY,
            cursorWidth.toFloat() + localCursorX + cursorXOffset,
            cursorHeight.toFloat() + cursorY,
            cursorRCorner.toFloat(),
            cursorLCorner.toFloat(),
            Paint().also {
                it.isAntiAlias = true
                it.color = cursorColor
                it.style = Style.FILL
        },)
        if(isCursorStrokeEnable) {
            canvas?.drawRoundRect(
                localCursorX.toFloat() + cursorXOffset,
                cursorY,
                cursorWidth.toFloat() + localCursorX + cursorXOffset,
                cursorHeight.toFloat()+ cursorY,
                cursorRCorner.toFloat(),
                cursorLCorner.toFloat(),
                Paint().also {
                    it.isAntiAlias = true
                    it.color = cursorStrokeColor
                    it.style = Style.STROKE
                    it.strokeWidth = cursorStrokeWidth
                }
            )
        }
    }
}