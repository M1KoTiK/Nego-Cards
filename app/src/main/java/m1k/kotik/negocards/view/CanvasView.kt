package m1k.kotik.negocards.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Paint.Style
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import com.squareup.picasso.Picasso
import m1k.kotik.negocards.data.canvas_qrc.model.*
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.ImageObject
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.QRCDecoder
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.ShapeObject
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.shapes.RectRShape
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


open class CanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    val currentSelectedObject: CanvasObject?
        get() {
            if (listCurrentSelectedObjects.isNotEmpty()) {
                return listCurrentSelectedObjects[0]
            }
            return null
        }

    var onCurrentSelectedObjectChange: () -> Unit = {}

    protected var listCurrentSelectedObjects: MutableList<CanvasObject> = mutableListOf()
    var canvas: Canvas? = null
    var myBitmap: Bitmap? = null
    protected val selectModeColor = "#905954E1"
    protected var GAP_WIDTH = 30
    protected var GAP_HEIGHT = 30
    protected var GAP_POSX = GAP_WIDTH / 2
    protected var GAP_POSY = GAP_HEIGHT / 2
    protected var canvasViewWidth = 900
    protected var canvasViewHeight = 600
    protected var objects_: MutableList<CanvasObject> = mutableListOf()
    fun getObjectsFromCode(code:String){
        objects_ = QRCDecoder().decode(code,backgroundObject)
    }
    val objects: List<CanvasObject>
        get() = objects_

    protected val strokeObject =  RectRShape()
    protected val strokeObjectPaint  = Paint().also {
        it.isAntiAlias = true
        it.isDither = true
        it.strokeWidth = 10f
        it.style = Style.STROKE
    }

    protected var backgroundObject: ShapeObject = RectRShape().apply {
        this.posY = 0
        this.posX = 0
        this.width = canvasViewWidth
        this.height = canvasViewHeight
        this.color = "FFFFFFFF"
    }
    protected val backgroundObjectPaint  = Paint().also {
        it.isAntiAlias = true
        it.isDither = true
        it.style = Style.FILL
        it.color = Color.WHITE
    }

    @SuppressLint("DrawAllocation")
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
                    strokeObject.drawWithCustomPaint(canvas, strokeObjectPaint.apply {
                        this.style = Paint.Style.STROKE
                        this.color = Color.parseColor(selectModeColor)
                        this.pathEffect = null
                    })
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
        backgroundObjectPaint.also {
            it.isAntiAlias = true
            it.isDither = true
            it.style = Style.FILL
            it.color = Color.WHITE
        }

        backgroundObject.apply {
            this.posY = 0
            this.posX = 0
            this.width = canvasViewWidth
            this.height = canvasViewHeight
            this.color = "FFFFFFFF"
        }
        backgroundObject.drawWithCustomPaint(canvas!!, backgroundObjectPaint)
        for (obj in objects_) {
            canvas.let {
                obj.draw(it)
            }
        }
        if (currentSelectedObject != null) {
            selectObject(currentSelectedObject!!, canvas!!)
        }

        this.invalidate()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(canvasViewWidth, canvasViewHeight)
    }

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

    fun clearSelectInSelectedObjects() {
        onCurrentSelectedObjectChange.invoke()
        for (obj in listCurrentSelectedObjects) {
            obj.isSelectMode = false
            obj.isEditMode = false
        }
    }

    fun findSelectedObject(x: Int, y: Int) {
        listCurrentSelectedObjects.clear()
        for (obj in objects_.reversed()) {
            if (obj.isCursorHoveredOver(x, y)) {
                listCurrentSelectedObjects.add(obj)
            }
        }
    }
    fun onEmptySelected() {
        clearSelectInAllObjects()
        listCurrentSelectedObjects.clear()
    }

    fun clearSelectInAllObjects() {
        onCurrentSelectedObjectChange.invoke()
        for (obj in objects_) {
            obj.isSelectMode = false
            obj.isEditMode = false

        }
    }
    companion object{

    }

}
