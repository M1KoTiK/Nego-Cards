package m1k.kotik.negocards.data.canvas_qrc.canvas_view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Toast
import m1k.kotik.negocards.data.canvas_qrc.old_govno.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.old_govno.CanvasObjectMode
import m1k.kotik.negocards.data.canvas_qrc.old_govno.CanvasSaver
import m1k.kotik.negocards.data.canvas_qrc.old_govno.canvas_object_types.CanvasObjectType
import m1k.kotik.negocards.data.canvas_qrc.old_govno.DoubleClickChecker
import m1k.kotik.negocards.data.canvas_qrc.old_govno.alert_dialogs.InputTextDialog
import m1k.kotik.negocards.data.canvas_qrc.old_govno.canvas_object_types.TextObject
import kotlin.math.max
import kotlin.math.min

open class CanvasEditor(context: Context, attrs: AttributeSet) : CanvasView(context, attrs) {
    private var inpTextPopup = InputTextDialog(context)

    init{
        inpTextPopup.setOnDismissListener{
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
    fun clearObjectsMode(){
        for(obj in objects_){
            obj.mode = CanvasObjectMode.None
        }
        invalidate()
    }

    fun saveInGallery(){
        clearObjectsMode()
        CanvasSaver.saveBitmapInGallery(CanvasSaver.getBitmapFromView(this),context)
    }





    private val editModeColor = "#90E10473"
    private val textInputModeColor = "#205954E1"
    private val dashEffect: DashPathEffect = DashPathEffect(floatArrayOf(10f, 10f), 5f)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        this.canvas = canvas
        fun selectObject(canvasObject: CanvasObject, canvas: Canvas) {
            try {
                strokeObject.posX = canvasObject.centerX - canvasObject.width / 2 - GAP_POSX
                strokeObject.posY = canvasObject.centerY - canvasObject.height / 2 - GAP_POSY
                strokeObject.width = canvasObject.width + GAP_WIDTH
                strokeObject.height = canvasObject.height + GAP_HEIGHT
                if (canvasObject.isSelectMode) {
                    strokeObject.drawWithCustomPaint(canvas,strokeObjectPaint.apply {
                        this.style = Paint.Style.STROKE
                        this.color = Color.parseColor(selectModeColor)
                        this.pathEffect = null
                    })
                }
                else if (canvasObject.isEditMode) {
                    strokeObject.drawWithCustomPaint(canvas, strokeObjectPaint.apply {
                        this.style = Paint.Style.STROKE
                        this.color = Color.parseColor(editModeColor)
                        this.pathEffect = dashEffect
                    })
                }
                else if(canvasObject.isInputMode){
                    strokeObject.drawWithCustomPaint(canvas, strokeObjectPaint.apply {
                        this.color = Color.parseColor(textInputModeColor)
                        this.pathEffect = null
                        this.style = Paint.Style.FILL
                    })
                    if(!inpTextPopup.isShowing){
                        inpTextPopup.initText = (currentSelectedObject!! as TextObject).text
                        inpTextPopup.show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
            this.invalidate()
        }
        for (obj in objects_) {
            canvas?.let {
                obj.draw(it)
            }
        }
        if (currentSelectedObject != null) {
            selectObject(currentSelectedObject!!, canvas!!)
        }
    }





    private val doubleClickChecker = DoubleClickChecker(200) {
        currentSelectedObject!!.isEditMode = true
        if(currentSelectedObject!!.type == CanvasObjectType.Text){
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