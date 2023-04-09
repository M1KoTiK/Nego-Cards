package m1k.kotik.negocards.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.model.PointModel
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectShape

open class CanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint: Paint = Paint()
    //var mDetector: GestureDetectorCompat = GestureDetectorCompat(context,context)
    val objects: List<CanvasObject>
        get() = objects_
    var STROKE_GAP_WIDTH = 30
    var STROKE_GAP_HEIGHT = 30
    var STROKE_GAP_POSX = STROKE_GAP_WIDTH/2
    var STROKE_GAP_POSY = STROKE_GAP_HEIGHT/2
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
        fun selectObject(canvasObject: CanvasObject, canvas: Canvas) {
            val myPath: Array<PointModel> = arrayOf<PointModel>(
                PointModel(canvasObject.centerX - canvasObject.width/2 -STROKE_GAP_POSX,
                    canvasObject.centerY - canvasObject.height/2 - STROKE_GAP_POSY ),
                PointModel(200, 200),
                PointModel(200, 400),
                PointModel(400, 400)
            )


            RectShape(canvasObject.centerX - canvasObject.width/2 -STROKE_GAP_POSX,
                canvasObject.centerY - canvasObject.height/2 - STROKE_GAP_POSY,
                canvasObject.width + STROKE_GAP_WIDTH,
                canvasObject.height + STROKE_GAP_HEIGHT,
                "905954E1",
                CanvasObject.CanvasObjectSerializationTag.Style.Stroke(),
                12).draw(canvas)
            this.invalidate()
        }
        super.onDraw(canvas)
        paint.apply {
            style = Paint.Style.FILL
            color = Color.WHITE
            isAntiAlias = true
            isDither = true
        }
        val rect = RectF(0f,0f,canvas?.width?.toFloat()!!,canvas?.height?.toFloat()!!)
        canvas?.drawRoundRect(rect,25f,25f,paint)
        for(obj in objects_){
            canvas?.let {
            if(obj.isSelected){
                selectObject(obj, canvas)
            }
                obj.draw(it)
            }
        }
        fun selectObject(canvasObject: CanvasObject) {
            RectShape(canvasObject.posX,
                canvasObject.posY+20,
                canvasObject.width+20,
                canvasObject.height+20,
                "FF",
                CanvasObject.CanvasObjectSerializationTag.Style.Stroke()).draw(canvas)
        }

    }

    override fun setOnTouchListener(l: OnTouchListener?) {
        super.setOnTouchListener(l)
    }
    private fun ClearSelected(){
        for (obj in objects_) {
            obj.isSelected = false
        }
    }
    var selectedObject: CanvasObject? = null
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.x.toInt()
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                ClearSelected()
                for (obj in objects_) {
                    if (obj.isCursorHoveredOver(x, y)) {
                        selectedObject = obj
                        obj.isSelected=true
                        this.invalidate()
                        break
                    }
                }

            }
            MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_MOVE -> {
                for (obj in objects_)
                {
                    if (obj.isSelected &&
                        x > obj.width/2 &&
                        y > obj.height/2 &&
                        x < this.width - obj.width/2 &&
                        y < this.height - obj.height/2)
                    {
                        obj.move(x,y)
                        this.invalidate()
                    }
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