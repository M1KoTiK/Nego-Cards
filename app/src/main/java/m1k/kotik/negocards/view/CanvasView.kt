package m1k.kotik.negocards.view

import android.content.Context
import android.graphics.*
import android.graphics.Color.parseColor
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.model.TextObject
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectShape

open class CanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint: Paint = Paint()
    //var mDetector: GestureDetectorCompat = GestureDetectorCompat(context,context)
    lateinit var mainCanvas: Canvas
    val objects: List<CanvasObject>
        get() = objects_

    protected var objects_: MutableList<CanvasObject> = mutableListOf()

    fun setCanvasObjects(objects: List<CanvasObject>) {
        objects_ = objects.toMutableList()
        this.invalidate()
    }
    fun clearCanvasObject(){
        objects_.clear()
        this.invalidate()
    }
    fun addCanvasObjects(canvasObject: CanvasObject) {
        objects_.add(canvasObject)
        this.invalidate()
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mainCanvas = canvas!!
        paint.apply {
            style = Paint.Style.FILL
            color = Color.WHITE
            isAntiAlias = true
            isDither = true
        }
        val rect = RectF(0f,0f,canvas?.width?.toFloat()!!,canvas?.height?.toFloat()!!)
        canvas?.drawRoundRect(rect,25f,25f,paint)
        for(obj in objects_){
            canvas?.let { obj.draw(it) }
        }

    }

    override fun setOnTouchListener(l: OnTouchListener?) {
        super.setOnTouchListener(l)
    }
    var selectedObject: CanvasObject? = null
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.x.toInt()
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_UP -> {
                for (obj in objects_) {
                    if (obj.isSelected(x, y)) {
                        selectedObject = obj
                        this.invalidate()
                        Toast.makeText(context, "${obj.type.visibleName}", Toast.LENGTH_SHORT).show()
                        break
                    } else {
                        selectedObject = null
                    }
                }
            }
            MotionEvent.ACTION_MOVE ->{
                if(selectedObject != null){
                    selectedObject!!.posX = x - selectedObject!!.width/2
                    selectedObject!!.posY = y + selectedObject!!.height/2
                    this.invalidate()
                }
            }
            }
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(900,600)
    }
    fun drawTriangle(canvas: Canvas, paint: Paint, x:Int, y:Int, width:Int) {
        val halfWidth : Int = width / 2
        val path: Path = Path()
        path.moveTo(x.toFloat(), (y - halfWidth).toFloat()) // Top
        path.lineTo((x - halfWidth).toFloat(), (y + halfWidth).toFloat()); // Bottom left
        path.lineTo((x + halfWidth).toFloat(), (y + halfWidth).toFloat()); // Bottom right
        path.lineTo(x.toFloat(), (y - halfWidth).toFloat()); // Back to Top
        path.close();
        canvas.drawPath(path, paint);
    }
    fun drawQuadrilateral(
        canvas: Canvas, paint: Paint, x: Int, y: Int,
        bottomLeftX: Int, bottomLeftY: Int,
        topLeftX: Int, topLeftY: Int,
        bottomRightX: Int, bottomRightY: Int,
        topRightX: Int, topRightY: Int,
    ) {

        val path: Path = Path()
        path.moveTo(x.toFloat(), y.toFloat()) // Top
        path.lineTo(bottomLeftX.toFloat(), bottomLeftY.toFloat())
        path.lineTo(topLeftX.toFloat(), topLeftY.toFloat())
        path.lineTo(topRightX.toFloat(), topRightY.toFloat())
        path.lineTo(bottomRightX.toFloat(), bottomRightY.toFloat())
        path.close();
        canvas.drawPath(path, paint);
    }
}