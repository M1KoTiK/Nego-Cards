package m1k.kotik.negocards.view

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.*
import android.graphics.Color.parseColor
import android.graphics.Paint.Style
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.model.DoubleClickChecker
import m1k.kotik.negocards.data.canvas_qrc.model.TextObject
import m1k.kotik.negocards.data.canvas_qrc.model.popup_windows.InputTextPopupWindow
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectRShape
import kotlin.math.max
import kotlin.math.min


open class CanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var inpTextPopup = InputTextPopupWindow()
    init{
        inpTextPopup.setup(context,300,1000)
        inpTextPopup.root!!.setOnDismissListener{
            if(currentSelectedObject!=null){
                currentSelectedObject!!.isSelectMode = true
                this.invalidate()
            }
        }
        inpTextPopup.onInpTextChange = {
            if(currentSelectedObject!=null && currentSelectedObject!!.isInputMode){
                (currentSelectedObject as TextObject).also {
                    it.text = inpTextPopup.inpText.text.toString()
                    it.reMeasure()
                }
            }
        }
    }
    private val paint: Paint = Paint()
    private var canvasViewWidth = 900
    private var canvasViewHeight = 600

    //var mDetector: GestureDetectorCompat = GestureDetectorCompat(context,context)
    val objects: List<CanvasObject>
        get() = objects_

    private var GAP_WIDTH = 30
    private var GAP_HEIGHT = 30
    private var GAP_POSX = GAP_WIDTH / 2
    private var GAP_POSY = GAP_HEIGHT / 2
    protected var objects_: MutableList<CanvasObject> = mutableListOf()

    var onCurrentSelectedObjectChange: () -> Unit = {}

    var canvas: Canvas? = null
    var myBitmap: Bitmap? = null

    fun setCanvasObjects(objects: List<CanvasObject>) {
        objects_ = objects.toMutableList()
        this.invalidate()
    }

    fun clearCanvasObject() {
        objects_.clear()
        this.invalidate()
    }

    fun deleteSelectedObject() {
        objects_.remove(currentSelectedObject)
        listCurrentSelectedObjects.clear()
        onCurrentSelectedObjectChange.invoke()
    }

    fun addCanvasObjects(canvasObject: CanvasObject) {
        objects_.add(canvasObject)
        this.invalidate()
    }

    fun changeViewSize(width: Int, height: Int) {
        canvasViewWidth = width
        canvasViewHeight = height
        requestLayout()
    }
    private val strokeObject =  RectRShape()
    private val strokeObjectPaint  = Paint().also {
        it.isAntiAlias = true
        it.isDither = true
        it.strokeWidth = 10f
        it.style = Style.STROKE
    }
    private val selectModeColor = "#905954E1"
    private val editModeColor = "#90E10473"
    private val textInputModeColor = "#205954E1"
    private val dashEffect: DashPathEffect = DashPathEffect(floatArrayOf(10f, 10f), 5f)

    var mWidth = context.resources.displayMetrics.widthPixels
    var mHeight = context.resources.displayMetrics.heightPixels

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        this.canvas = canvas
        @SuppressLint("DrawAllocation")
        fun selectObject(canvasObject: CanvasObject, canvas: Canvas) {
            try {
                strokeObject.posX = canvasObject.centerX - canvasObject.width / 2 - GAP_POSX
                strokeObject.posY = canvasObject.centerY - canvasObject.height / 2 - GAP_POSY
                strokeObject.width = canvasObject.width + GAP_WIDTH
                strokeObject.height = canvasObject.height + GAP_HEIGHT
                if (canvasObject.isSelectMode) {
                    strokeObject.drawWithCustomPaint(canvas,strokeObjectPaint.apply {
                        this.style = Style.STROKE
                        this.color = parseColor(selectModeColor)
                        this.pathEffect = null
                    })
                }
                else if (canvasObject.isEditMode) {
                    strokeObject.drawWithCustomPaint(canvas, strokeObjectPaint.apply {
                        this.style = Style.STROKE
                        this.color = parseColor(editModeColor)
                        this.pathEffect = dashEffect
                    })
                }
                else if(canvasObject.isInputMode){
                    strokeObject.drawWithCustomPaint(canvas, strokeObjectPaint.apply {
                        this.color = parseColor(textInputModeColor)
                        this.pathEffect = null
                        this.style = Style.FILL
                    })
                    if(!inpTextPopup.isOpen){
                        inpTextPopup.initText = (currentSelectedObject!! as TextObject).text
                        inpTextPopup.show(0,-200,
                            Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
            this.invalidate()
        }

        paint.apply {
            style = Paint.Style.FILL
            color = Color.WHITE
            isAntiAlias = true
            isDither = true
        }
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas?.drawRoundRect(rect, 25f, 25f, paint)
        for (obj in objects_) {
            canvas?.let {
                obj.draw(it)
            }
        }
        if (currentSelectedObject != null) {
            selectObject(currentSelectedObject!!, canvas!!)
        }
    }

    open fun onEmptySelected() {
        clearSelectInAllObjects()
        listCurrentSelectedObjects.clear()

    }

    override fun setOnTouchListener(l: OnTouchListener?) {
        super.setOnTouchListener(l)
    }

    private var listCurrentSelectedObjects: MutableList<CanvasObject> = mutableListOf()

    private fun clearSelectInAllObjects() {
        onCurrentSelectedObjectChange.invoke()
        for (obj in objects_) {
            obj.isSelectMode = false
            obj.isEditMode = false

        }
    }

    private fun clearSelectInSelectedObjects() {
        onCurrentSelectedObjectChange.invoke()
        for (obj in listCurrentSelectedObjects) {
            obj.isSelectMode = false
            obj.isEditMode = false
        }
    }

    val currentSelectedObject: CanvasObject?
        get() {
            if (listCurrentSelectedObjects.isNotEmpty()) {
                return listCurrentSelectedObjects[0]
            }
            return null
        }

    private fun findSelectedObject(x: Int, y: Int) {
        listCurrentSelectedObjects.clear()
        for (obj in objects_.reversed()) {
            if (obj.isCursorHoveredOver(x, y)) {
                listCurrentSelectedObjects.add(obj)
            }
        }
    }

    private val doubleClickChecker = DoubleClickChecker(200) {
        currentSelectedObject!!.isEditMode = true
        if(currentSelectedObject!!.type == CanvasObject.CanvasObjectType.Text){
            currentSelectedObject!!.isInputMode = true
        }
    }
    private var startX: Int = 0
    private var startY: Int = 0
    private var transformInitialWidth: Int = 0
    private var transformInitialHeight: Int = 0
    private var transformInitialPosX: Int = 0
    private var transformInitialPosY: Int = 0
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return true
        val x = event.x.toInt()
        val y = event.y.toInt()
        val deltaX = x - startX
        val deltaY = y - startY

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x.toInt()
                startY = event.y.toInt()
                val selectedObject = currentSelectedObject
                if (selectedObject?.isEditMode == true){
                    transformInitialWidth = selectedObject.width
                    transformInitialHeight = selectedObject.height

                }
                findSelectedObject(x, y)
                if (listCurrentSelectedObjects.isNotEmpty()) {
                    currentSelectedObject!!.isSelectMode = true
                        transformInitialPosX = currentSelectedObject!!.posX
                        transformInitialPosY = currentSelectedObject!!.posY
                    doubleClickChecker.click()

                }
                if (listCurrentSelectedObjects.isEmpty()) {
                    onEmptySelected()
                }


                onCurrentSelectedObjectChange.invoke()
                this.invalidate()
            }
            MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_MOVE -> {
                val selectedObject = currentSelectedObject
                if (selectedObject != null) {
                    if (selectedObject.isEditMode) {
                        //Действия с объектами при режиме трансформации
                        selectedObject.width = min(max(transformInitialWidth + deltaX, MIN_OBJECT_SIZE),this.width - transformInitialPosX )
                        selectedObject.height = min(max(transformInitialHeight + deltaY, MIN_OBJECT_SIZE), this.height - transformInitialPosY)
//
                    } else if (selectedObject.isSelectMode) {
                        //Действия с объектами при режиме перемещения
                            currentSelectedObject!!.move(
                                max(transformInitialPosX + deltaX, MIN_OBJECT_POS),
                                max(transformInitialPosY + deltaY, MIN_OBJECT_POS)
                            )
                    }
                }
                this.invalidate()
                onCurrentSelectedObjectChange.invoke()
            }
        }
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(canvasViewWidth, canvasViewHeight)

    }


    fun drawTriangle(canvas: Canvas, paint: Paint, x: Int, y: Int, width: Int) {
        val halfWidth: Int = width / 2
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

    companion object {
        const val MIN_OBJECT_SIZE: Int = 30
        const val MIN_OBJECT_POS: Int = 0
    }
}
