package m1k.kotik.negocards.data.canvas_qrc.canvas_view.new_canvas_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.CanvasShape
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Rectangle
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.texts.CanvasText

class CanvasConfig(context: Context, attrs: AttributeSet) : View(context, attrs) {
    var canvasCorner = 5
    val background: CanvasShape = Rectangle().also {
        it.x = 0
        it.y = 0
    }
    var canvasHeight: Int = 600
    var canvasWidth: Int = 800

    val backgroundPaint = Paint().also {
        it.isAntiAlias = true
        it.isDither = true
        it.style = Paint.Style.FILL
        it.color = Color.parseColor("#F9F9F9")
    }

    val verticalMeasureLine =  Rectangle().also {
        it.x = marginMeasureLine
        it.width = sizeForMeasureLine
    }
    val horizontalMeasureLine =  Rectangle().also {
        it.y = marginMeasureLine
        it.height = sizeForMeasureLine
    }

    val measureLinePaint  = Paint().also {
        it.isAntiAlias = true
        it.isDither = true
        it.strokeWidth = 10f
        it.style = Paint.Style.FILL
    }

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

        background.also {
            it.width = canvasWidth
            it.height = canvasHeight
            it.paint = backgroundPaint
        }.draw(canvas)

        verticalMeasureLine.also {
            it.height = canvasHeight
            it.paint = measureLinePaint
        }.draw(canvas)

        horizontalMeasureLine.also {
            it.width = canvasWidth
            it.paint = measureLinePaint
        }.draw(canvas)

        verticalSizeCaption.also {
            it.text = canvasHeight.toString()
            it.paint = captionPaint
            it.x = verticalMeasureLine.x + captionMargin
            it.y = canvasHeight/2
        }.draw(canvas)

        horizontalSizeCaption.also {
            it.text = canvasWidth.toString()
            it.paint = captionPaint
            it.x = canvasWidth/2
            it.y = horizontalMeasureLine.y +
                    captionMargin +
                    getTextHeight(captionPaint, it.text)
        }.draw(canvas)
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