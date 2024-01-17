package m1k.kotik.negocards.custom_views.color_picker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.CanvasShape
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.RoundRectangle
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.new_canvas_view.CanvasEditor
import m1k.kotik.negocards.data.canvas_qrc.old_govno.getDistanceBetweenPoints

class HueAndSaturationCirclePicker(context: Context, attrs: AttributeSet) : View(context, attrs) {
//================= Важные приколы для использования контрола ===========================
    var selectedHue:Float = 0f
    var selectedSaturation:Float = 0f
    var onSelecting: ((hue:Float, saturation:Float) -> Unit)? = null

//================ Параметры круга ================
    var circleDiameter: Int = 500
    var circleX:Int = 0
    var circleY:Int = 0
//=============== Параметры курсора ===============
    var cursorX: Int = circleX/2
    var cursorY: Int = circleY/2
    var cursorWidth:Int = 40
    var cursorHeight:Int = 40
    var cursorColor:Int = 0xD9D9D9
    var cursorStrokeColor: Int = 0x181818
    var cursorCorner:Int = 7
    var cursor: CanvasShape = RoundRectangle()

//
    private var startX: Int = 0
    private var startY: Int = 0
    private var initialPosX = 0
    private var initialPosY = 0
    private var naturalDistance:Double = 0.0
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false
        val deltaX = event.x - startX
        val deltaY = event.y - startY
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x.toInt()
                startY = event.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                cursorX =(initialPosX + deltaX).toInt().
                coerceIn(circleX.. circleX + circleDiameter)

                cursorY = (initialPosY + deltaY ).toInt().
                coerceIn(circleX.. circleY + circleDiameter)
            }
        }
        val naturalX = event.x.toInt()
        val naturalY = event.y.toInt()
        val circleCenter = circleX + circleDiameter/2
        naturalDistance = getDistanceBetweenPoints(
            circleCenter.toFloat(),
            circleCenter.toFloat(),
            naturalX.toFloat(),
            naturalY.toFloat()
        )
        val relativeX = naturalX - circleCenter
        val relativeY = naturalY - circleCenter
        if(relativeX != 0) {
            var angle = kotlin.math.atan2(relativeY.toDouble(), relativeX.toDouble())/ Math.PI *180
            if(angle<0) {
                angle += 360
            }
            selectedHue = angle.toFloat()
        }
            selectedSaturation = (naturalDistance / circleDiameter/2).toFloat()
        return true
    }



    override fun onDraw(canvas: Canvas?) {
        canvas ?: return
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
//================== Отрисовка круга ==========================
        val circleDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.color_circle, null) ?: return
        circleDrawable.setBounds(circleX,circleY,circleDiameter,circleDiameter)
        circleDrawable.draw(canvas)
    }
}