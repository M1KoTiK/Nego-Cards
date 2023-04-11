package m1k.kotik.negocards.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Color.parseColor
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.model.DoublecClickChecker
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectRShape


open class CanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint: Paint = Paint()
    private var canvasViewWidth = 900
    private var canvasViewHeight = 600
    //var mDetector: GestureDetectorCompat = GestureDetectorCompat(context,context)
    val objects: List<CanvasObject>
        get() = objects_
    private var GAP_WIDTH = 30
    private var GAP_HEIGHT = 30
    private var GAP_POSX = GAP_WIDTH/2
    private var GAP_POSY = GAP_HEIGHT/2
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
    fun changeViewSize(width:Int,height:Int){
        canvasViewWidth = width
        canvasViewHeight = height
        requestLayout()
    }
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        fun selectObject(canvasObject: CanvasObject, canvas: Canvas) {
            val paintSelect = Paint().also {
                it.strokeWidth = 10f
                it.style = Paint.Style.STROKE
                it.isAntiAlias = true
                it.color = parseColor("#905954E1")
            }
            val dashPaintEdit = Paint().also {
                it.strokeWidth = 10f
                it.style = Paint.Style.STROKE
                it.isAntiAlias = true
                it.pathEffect = DashPathEffect(floatArrayOf(10f,10f),5f)
                it.color = parseColor("#90E10473")
            }
            try {
                if (canvasObject.isSelectMode) {
                    RectRShape(
                        20,
                        20,
                        canvasObject.centerX - canvasObject.width / 2 - GAP_POSX,
                        canvasObject.centerY - canvasObject.height / 2 - GAP_POSY,
                        canvasObject.width + GAP_WIDTH,
                        canvasObject.height + GAP_HEIGHT,
                        "905954E1",
                        CanvasObject.CanvasObjectSerializationTag.Style.Stroke()).drawRectRWithCustomPaint(
                        canvas,
                        paintSelect)
                }
                if (canvasObject.isEditMode) {
                    RectRShape(
                        20,
                        20,
                        canvasObject.centerX - canvasObject.width / 2 - GAP_POSX,
                        canvasObject.centerY - canvasObject.height / 2 - GAP_POSY,
                        canvasObject.width + GAP_WIDTH,
                        canvasObject.height + GAP_HEIGHT,
                        "905954E1",
                        CanvasObject.CanvasObjectSerializationTag.Style.Stroke()).drawRectRWithCustomPaint(
                        canvas,
                        dashPaintEdit)
                }
            }
            catch (e:Exception){
                Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
            }
            this.invalidate()
        }

        paint.apply{
            style = Paint.Style.FILL
            color = Color.WHITE
            isAntiAlias = true
            isDither = true
        }
        val rect = RectF(0f,0f, width.toFloat(), height.toFloat())
        canvas?.drawRoundRect(rect,25f,25f,paint)
        for(obj in objects_)
        {
            canvas?.let {
                obj.draw(it)
            }
        }
        if(currentSelectedObject!=null)
        {
            selectObject(currentSelectedObject!!, canvas!!)
            if(currentSelectedObject!!.isEditMode)
            {
                //Toast.makeText(context,"${selectedObject!!.type.visibleName} ${selectedObject!!.selectedCount}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    open fun onEmptySelected(){
        clearSelectInAllObjects()
        listCurrentSelectedObjects.clear()

    }
    override fun setOnTouchListener(l: OnTouchListener?) {
        super.setOnTouchListener(l)
    }
    private var listCurrentSelectedObjects: MutableList<CanvasObject> = mutableListOf()

    private fun clearSelectInAllObjects(){
        for (obj in objects_) {
            obj.isSelectMode = false
            obj.isEditMode = false

        }
    }
    private fun clearSelectInSelectedObjects(){
        for (obj in listCurrentSelectedObjects) {
            obj.isSelectMode = false
            obj.isEditMode = false
        }
    }
    private val currentSelectedObject: CanvasObject?
        get(){
            if(listCurrentSelectedObjects.isNotEmpty()){
                return listCurrentSelectedObjects[0]
            }
            return null
        }

    private fun findSelectedObject(x: Int,y: Int){
        listCurrentSelectedObjects.clear()
        for (obj in objects_)
        {
            if (obj.isCursorHoveredOver(x, y))
            {
                listCurrentSelectedObjects.add(obj)
            }
        }
    }
    val doubleClickChecker = DoublecClickChecker(200){
        currentSelectedObject!!.isEditMode = true
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val x = event!!.x.toInt()
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                    findSelectedObject(x,y)
                    if(listCurrentSelectedObjects.isNotEmpty()) {
                        currentSelectedObject!!.isSelectMode = true
                        doubleClickChecker.click()
                    }
                    if(listCurrentSelectedObjects.isEmpty())
                    {
                        onEmptySelected()
                    }
                this.invalidate()
            }
            MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_MOVE -> {
                if(currentSelectedObject!=null) {
                    if (currentSelectedObject!!.isEditMode)
                    {
                        //Действия с объектами при режиме редактирования
                        if (x < currentSelectedObject!!.posX + currentSelectedObject!!.width + 80 &&
                            x > currentSelectedObject!!.posX + currentSelectedObject!!.width - 80
                        ) {
                            currentSelectedObject!!.width = kotlin.math.abs(x - currentSelectedObject!!.posX)
                            this.invalidate()
                        }
                    }
                    else if(currentSelectedObject!!.isSelectMode)
                    {
                        //Действия с объектами при режиме перемещения
                        if (
                            x > currentSelectedObject!!.width/2 &&
                            y > currentSelectedObject!!.height/2 &&
                            x < this.width - currentSelectedObject!!.width/2 &&
                            y < this.height - currentSelectedObject!!.height/2)
                        {
                            currentSelectedObject!!.move(x, y)
                            this.invalidate()
                        }
                        else if(y > currentSelectedObject!!.height/2 &&  y < this.height - currentSelectedObject!!.height/2)
                        {
                            currentSelectedObject!!.move(currentSelectedObject!!.centerX,y)
                            this.invalidate()
                        }
                        else if (x > currentSelectedObject!!.width/2 &&  x < this.width - currentSelectedObject!!.width/2)
                        {
                            currentSelectedObject!!.move(x, currentSelectedObject!!.centerY)
                            this.invalidate()
                        }
                    }
                }
            }
        }
        return true
    }




    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(canvasViewWidth,canvasViewHeight)
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