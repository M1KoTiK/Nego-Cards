package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_editors

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Line
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.RoundRectangle
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.texts.CanvasText

class CanvasConfig(context: Context, attrs: AttributeSet) : View(context, attrs) {

    var canvasCorner = 5
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    val background: RoundRectangle = RoundRectangle().also {
        it.x = 0
        it.y = 0
        it.leftCorner = 20
        it.rightCorner = 20
    }
    var canvasHeight: Int = 600
    set(value) {
        field = value
        invalidate()
        requestLayout()
    }
    var canvasWidth: Int = 800
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }
    var canvasColor = Color.parseColor("#F9F9F9")
    set(value) {
        field = value
        invalidate()
        requestLayout()
    }
    init{
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CanvasConfig)
        canvasHeight = typedArray.getInteger(R.styleable.CanvasConfig_CanvasHeight,600)
        canvasWidth = typedArray.getInteger(R.styleable.CanvasConfig_CanvasWidth,800)
        typedArray.recycle()
        invalidate()
        requestLayout()
    }
    val backgroundPaint = Paint().also {
        it.isAntiAlias = true
        it.isDither = true
        it.style = Paint.Style.FILL
        it.color = canvasColor
    }

    val measureLinePaint  = Paint().also {
        it.strokeWidth = 6f
        it.isDither = true
        it.isAntiAlias = true
        it.style = Paint.Style.STROKE
        it.pathEffect = DashPathEffect(floatArrayOf(30f,8f), 0f)
    }
    val line = Line()
    val captionPaint = Paint().also {
        it.isAntiAlias = true
        it.isDither = true
        it.textSize = 40f
    }

    val horizontalSizeCaption = CanvasText()
    val verticalSizeCaption = CanvasText()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        backgroundPaint.also {
            it.color = canvasColor
        }
        background.also {
            it.width = canvasWidth
            it.height = canvasHeight
            it.paint = backgroundPaint
            it.leftCorner = canvasCorner
            it.rightCorner = canvasCorner
        }.draw(canvas)

        line.also {
            it.x1 = marginMeasureLine
            it.y1 = 0
            it.x2 = marginMeasureLine
            it.y2 = canvasHeight
            it.paint = measureLinePaint
        }.draw(canvas)

        line.also {
            it.x1 = 0
            it.y1 = marginMeasureLine
            it.x2 = canvasWidth
            it.y2 = marginMeasureLine
            it.paint = measureLinePaint
        }.draw(canvas)

        canvas.drawText(
            canvasHeight.toString(),
            marginMeasureLine + captionMargin.toFloat(),
            canvasHeight/2f,
            captionPaint
        )
        val margin = captionMargin + getTextHeight(captionPaint, canvasWidth.toString())
        canvas.drawText(
            canvasWidth.toString(),
            canvasWidth/2f,
            marginMeasureLine + margin.toFloat(),
            captionPaint
        )

    }
    private fun getTextHeight(textPaint: Paint, text: String): Int{
        val bounds = Rect()
        textPaint.getTextBounds(text, 0, text.length, bounds)
        val height: Int = bounds.height()
        return height
    }
    companion object{
        const val marginMeasureLine = 45
        const val sizeForMeasureLine = 5
        const val captionMargin = 15
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = canvasWidth
        val desiredHeight = canvasHeight
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

}