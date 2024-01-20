package m1k.kotik.negocards.custom_views.color_picker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.math.MathUtils
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.CanvasShape
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.RoundRectangle
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.new_canvas_view.CanvasEditor
import m1k.kotik.negocards.data.canvas_qrc.old_govno.getDistanceBetweenPoints
import m1k.kotik.negocards.data.canvas_qrc.old_govno.isAreaContainsPoint
import m1k.kotik.negocards.data.canvas_qrc.old_govno.parseColorFromString

class HueAndSaturationCirclePicker(context: Context, attrs: AttributeSet) : View(context, attrs) {
//================= Важные приколы для использования контрола ===========================
    var selectedHue:Float = 0f
    var selectedSaturation:Float = 0f
    var onSelecting: ((hue:Float, saturation:Float) -> Unit)? = null
    fun setColor(color: Color){

    }
//=============== Параметры курсора ===============
    var cursorX: Int = 0
    var cursorY: Int = 0
    var cursorWidth:Int = 40
    var cursorHeight:Int = 40
    var cursorColor:Int = Color.parseColor("#CCCCCC")
    var cursorStrokeColor: Int = Color.parseColor("#181818")
    var cursorCorner:Int = 7
    var cursor: CanvasShape = RoundRectangle()

//================ Параметры круга ================
    var circleDiameter: Int = 500
    var circleX:Int = width/2 - circleDiameter/2
        private set
    var circleY:Int = height/2 - circleDiameter/2
        private set
//
    private var startX: Int = 0
    private var startY: Int = 0
    private var initialPosX = 0
    private var initialPosY = 0
    private var naturalDistance:Double = 0.0
    private var canCursorMove:Boolean = false
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false
        val deltaX = event.x - startX
        val deltaY = event.y - startY

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x.toInt()
                startY = event.y.toInt()

                cursorX = (startX - cursorWidth/2).
                coerceIn(circleX - cursorWidth/2.. circleX + circleDiameter - cursorWidth/2)

                cursorY = (startY - cursorHeight/2).
                coerceIn(circleX - cursorHeight/2.. circleX + circleDiameter - cursorWidth/2)

                initialPosX = cursorX
                initialPosY = cursorY
                canCursorMove = isAreaContainsPoint(
                    Point(startX, startY),
                    Point(cursorX, cursorY),
                    Point(cursorX + cursorWidth, cursorY + cursorHeight))

            }
            MotionEvent.ACTION_MOVE -> {
                if(canCursorMove){
                    cursorX =(initialPosX + deltaX).toInt().
                    coerceIn(circleX - cursorHeight/2.. circleX + circleDiameter - cursorWidth/2)

                    cursorY = (initialPosY + deltaY).toInt().
                    coerceIn(circleX - cursorHeight/2.. circleX + circleDiameter - cursorWidth/2)
                }


            }
        }
        val naturalX = cursorX + cursorWidth/2
        val naturalY = cursorY + cursorHeight/2
        val circleCenterX = circleX + circleDiameter/2
        val circleCenterY = circleY + circleDiameter/2
        naturalDistance = getDistanceBetweenPoints(
            circleCenterX.toFloat(),
            circleCenterY.toFloat(),
            naturalX.toFloat(),
            naturalY.toFloat()
        )
        println(naturalDistance)
        val relativeX = naturalX - circleCenterX
        val relativeY = naturalY - circleCenterY
        if(relativeX != 0) {
            var angle = kotlin.math.atan2(relativeY.toDouble(), relativeX.toDouble())/ Math.PI *180
            if(angle<0) {
                angle += 360
            }
            selectedHue = angle.toFloat()

        }
        selectedSaturation = (naturalDistance / (circleDiameter/2)).toFloat()
        cursorColor = Color.HSVToColor(255, floatArrayOf(selectedHue, selectedSaturation, 1f))
        onSelecting?.let { it(selectedHue, selectedSaturation) }
        invalidate()
        return true
    }



    override fun onDraw(canvas: Canvas?) {
        canvas ?: return
        circleX = width/2 - circleDiameter/2
        circleY = height/2 - circleDiameter/2
//================== Отрисовка круга ==========================
        val circleDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.color_circle, null) ?: return
        circleDrawable.setBounds(circleX,circleY,circleX + circleDiameter, circleY + circleDiameter)
        circleDrawable.draw(canvas)

//=================== Отрисовка курсора =====================
        cursor.also {
            it.x = cursorX
            it.y = cursorY
            it.width = cursorWidth
            it.height = cursorHeight
            it.paint.apply {
                this.style = Paint.Style.FILL
                this.color = cursorColor
            }
        }.draw(canvas)
        //Здесь снова рисуем чтобы получилась обводка
        cursor.also {
            it.paint.apply {
                this.style = Paint.Style.STROKE
                this.color = cursorStrokeColor
            }
        }.draw(canvas)
    }

//
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = circleDiameter + cursorWidth*2
        val desiredHeight = circleDiameter + cursorHeight*2
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

