package m1k.kotik.negocards.custom_views.canvas

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.shapes.RectRShape

class ScannedQRCViewer(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var backgroundWidth: Int = 900
    private var backgroundheight: Int = 600
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    var backgroundShape = RectRShape().also {
        it.width = backgroundWidth
        it.height = backgroundheight
        it.posY = 0
        it.posX = 0
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        backgroundShape.draw(canvas!!)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(backgroundWidth, backgroundheight)
    }
}