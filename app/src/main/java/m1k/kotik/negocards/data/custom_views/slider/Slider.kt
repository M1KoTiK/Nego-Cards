package m1k.kotik.negocards.data.custom_views.slider

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.toColorInt
import com.google.mlkit.common.model.CustomRemoteModel
import m1k.kotik.negocards.R
import kotlin.math.abs

class Slider (context: Context, attrs: AttributeSet) : View(context, attrs) {
    var startValue = 0f
    set(value) {
        field = value
        invalidate()
    }
    var endValue = 100f
        set(value) {
            field = value
            invalidate()
        }
    var step = 1f
        set(value) {
            field = value
            invalidate()
        }

//====================================
//Свойства задней панели (Stripe)
//====================================
    private var stripeHeight = 65
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
    private var stripeWidth = 200
        set(value){
            field = value
            invalidate()
            requestLayout()
        }
    private var stripeRCorner = 10
        set(value) {
            field = value
            invalidate()
        }
    private var stripeLCorner = 10
        set(value) {
            field = value
            invalidate()
        }
    private val stripeX
    get() = width/2 - stripeWidth/2

    private val stripeY: Float
        get() = height.toFloat()/2 - stripeHeight.toFloat()/2
//====================================
//Свойства курсора (Cursor)
//====================================
    private var cursorHeight = 100
        set(value) {
            field = value
            invalidate()
        }
    private var cursorWidth = 100
        set(value) {
            field = value
            invalidate()
        }
    private val defaultCursorX: Int
    get() {
        return stripeX
    }
    private var cursorX: Int? = null
        set(value) {
            field =value
            invalidate()
            requestLayout()
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
    private var cursorLCorner = 10
        set(value) {
            field = value
            invalidate()
        }
    private var cursorRCorner = 10
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
    private var isStrokeEnabled = true
        set(value) {
            field = value
            invalidate()
        }
    init {
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.Slider)
        stripeHeight = typedArray.getDimension(R.styleable.Slider_stripeHeight,115f).toInt()
        stripeWidth = typedArray.getDimension(R.styleable.Slider_stripeWidth,600f).toInt()
        stripeColor = typedArray.getColor(R.styleable.Slider_stripeColor,"#D9D9D9".toColorInt())
        stripeRCorner = typedArray.getDimension(R.styleable.Slider_stripeRCorner,10f).toInt()
        stripeLCorner = typedArray.getDimension(R.styleable.Slider_stripeLCorner,10f).toInt()
        cursorHeight = typedArray.getDimension(R.styleable.Slider_cursorHeight,100f).toInt()
        cursorWidth = typedArray.getDimension(R.styleable.Slider_cursorWidth,100f).toInt()
        typedArray.recycle()
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
        cursorX = x.toInt()
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = stripeWidth
        val desiredHeight = stripeHeight
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

    /**
     *Реальный margin будет в два раза меньше в количественном эквиваленте
     */
    private var marginX = 0
    /**
     *Реальный margin будет в два раза меньше в количественном эквиваленте
     */
    private var marginY = 0
    private fun drawStripe(canvas: Canvas?){
        canvas?.drawRoundRect(
            stripeX.toFloat(),
            stripeY +(2 * marginY),
            stripeWidth.toFloat() + (2 * marginX) + stripeX,
            stripeHeight.toFloat() + stripeY,
            stripeRCorner.toFloat(),stripeLCorner.toFloat(),
            Paint().also {
                it.color = stripeColor
                it.style = Style.FILL
            }
        )
        if(isStrokeEnabled) {
            canvas?.drawRoundRect(
                stripeX.toFloat(),
                stripeY + (2 * marginY),
                stripeWidth.toFloat() + stripeX +  (2 * marginX),
                stripeHeight.toFloat() + stripeY,
                stripeRCorner.toFloat(),
                stripeLCorner.toFloat(),
                Paint().also {
                    it.color = stripeStrokeColor
                    it.style = Style.STROKE
                    it.strokeWidth = 10f
                })
        }
    }
    private fun drawCursor(canvas: Canvas?){
        var localCursorX = defaultCursorX
        if(cursorX != null){
            localCursorX = cursorX!!
        }
        canvas?.drawRoundRect(
            localCursorX.toFloat() + marginX,
            cursorY + marginY,
            cursorWidth.toFloat() + localCursorX,
            cursorHeight.toFloat() + cursorY,
            cursorRCorner.toFloat(),
            cursorLCorner.toFloat(),
            Paint().also {
                it.color = cursorColor
                it.style = Style.FILL
        },)
        if(isStrokeEnabled) {
            canvas?.drawRoundRect(
                localCursorX.toFloat(),
                cursorY + marginY,
                cursorWidth.toFloat() + localCursorX,
                cursorHeight.toFloat()+ cursorY,
                cursorRCorner.toFloat(),
                cursorLCorner.toFloat(),
                Paint().also {
                    it.color = cursorStrokeColor
                    it.style = Style.STROKE
                    it.strokeWidth = 10f
                }
            )
        }
    }
}