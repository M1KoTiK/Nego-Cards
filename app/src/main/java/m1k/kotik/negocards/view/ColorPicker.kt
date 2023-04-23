package m1k.kotik.negocards.view

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import m1k.kotik.negocards.R


class ColorPicker(context: Context, attrs: AttributeSet) : View(context, attrs) {

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val myIcon = ResourcesCompat.getDrawable(resources, R.drawable.color_circle, null);
        myIcon!!.draw(canvas!!)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}