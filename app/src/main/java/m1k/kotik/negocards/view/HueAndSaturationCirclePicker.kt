package m1k.kotik.negocards.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color.HSVToColor
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.model.*
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectRShape
import java.lang.Math.*

class HueAndSaturationCirclePicker (context: Context, attrs: AttributeSet) : View(context, attrs) {
    var selectedHue:Float? = null
    var selectedSaturation:Float? = null
    var onHueAndSaturationChange: ((hue:Float, saturation:Float) -> Unit)? = null
    var selectedColor:Int? = null
    private var colorPointerX:Int? = null
    private var colorPointerY:Int? = null
    private var circleHeight = 600
    private var circleWidth = 600
    private var circleX = 0
    private var circleY = 0
    private var naturalDistance:Double? = null

    private var colorPointer :ShapeObject = RectRShape(10,10,0,0,60,60,"AA181818",
        CanvasObject.CanvasObjectSerializationTag.Style.Stroke(),5)

    val circleRadius:Double  get(){
        return (height/2).toDouble()
    }
    fun getHex():String?{
        if(selectedColor!=null){
           return getHexString(selectedColor!!)
        }
        return null
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        fun calculate(){
            val naturalX = event!!.x.toInt()
            val naturalY = event!!.y.toInt()
            val centerX = circleX + circleWidth/2
            val centerY = circleY + circleHeight/2
            naturalDistance = getDistanceBetweenPoints(
                centerX.toFloat(),
                centerY.toFloat(),
                naturalX.toFloat(),
                naturalY.toFloat()
            )
            val relativeX = naturalX - centerX
            val relativeY = naturalY - centerY
            if(relativeX != 0) {
                var angle = kotlin.math.atan2(relativeY.toDouble(), relativeX.toDouble())/PI*180
                if(angle<0) {
                    angle += 360
                }
                selectedHue = angle.toFloat()
            }
            selectedSaturation = (naturalDistance!! /circleRadius).toFloat()
            selectedColor = HSVToColor(255, floatArrayOf(selectedHue!!,selectedSaturation!!,1f))
        }
        when(event!!.action){
            MotionEvent.ACTION_MOVE ->{
                colorPointerX = event.x.toInt()
                colorPointerY = event.y.toInt()
                colorPointer.move(colorPointerX!!, colorPointerY!!)
            }
            MotionEvent.ACTION_DOWN->{
                colorPointerX = event.x.toInt()
                colorPointerY = event.y.toInt()
                colorPointer.move(colorPointerX!!,colorPointerY!!)
            }
            MotionEvent.ACTION_UP->{

            }
        }
        if(onHueAndSaturationChange!=null && selectedHue!=null && selectedSaturation!=null){
            onHueAndSaturationChange!!.invoke(selectedHue!!,selectedSaturation!!)
        }
        calculate()
        this.invalidate()
        return true
    }


    fun setCircleSize(size:Int){
        circleWidth = size
        circleHeight = size
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val myIcon = ResourcesCompat.getDrawable(resources, R.drawable.color_circle, null);
        myIcon!!.setBounds(circleX,circleY,circleWidth,circleHeight)
        myIcon.draw(canvas!!)
        if(colorPointerX!= null && colorPointerY!=null){
            colorPointer.color = CanvasObject.CanvasObjectSerializationTag.Color.default
            colorPointer.style = CanvasObject.CanvasObjectSerializationTag.Style.Stroke()
            colorPointer.draw(canvas)
            if(selectedColor !=null) {
                colorPointer.style = CanvasObject.CanvasObjectSerializationTag.Style.Fill()
                colorPointer.color = getHexString(selectedColor!!)
                colorPointer.draw(canvas)
            }

        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(600,600)
    }
}