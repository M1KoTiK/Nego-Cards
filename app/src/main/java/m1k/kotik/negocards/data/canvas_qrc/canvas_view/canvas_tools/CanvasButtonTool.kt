package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.internal.ContextUtils.getActivity
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.BitmapShape
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Oval
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.RoundRectangle
import m1k.kotik.negocards.data.canvas_qrc.old_govno.parseColorFromString

abstract class CanvasButtonTool<T>: AutoPositionableCanvasTool<T>() {
    override var width: Int = 65
    override var height: Int = 65
    open var icon: Drawable? = null
    var isVisible = true
    open var backgroundColor: Int = parseColorFromString("#EEEEEE")
    open var backgroundObject: BitmapShape = Oval()

    override fun draw(canvas: Canvas) {
        if (objectsForEdit.isNotEmpty() && isVisible) {
            val desiredPosition = onPositioning(objectsForEdit)
            this.x = desiredPosition.x
            this.y = desiredPosition.y

            val backgroundPaint = Paint().apply {
                this.color = backgroundColor
                this.style = Paint.Style.FILL
                this.isAntiAlias = true
            }
            backgroundObject.also {
                it.x = desiredPosition.x
                it.y = desiredPosition.y
                it.width = width
                it.height = height
                it.paint = backgroundPaint
            }
            backgroundObject.draw(canvas)

            if (icon != null) {
                icon!!.setBounds(x + 10 ,y + 10,x+width -10,y+height - 10)
                icon!!.draw(canvas)
            }
        }
    }

}