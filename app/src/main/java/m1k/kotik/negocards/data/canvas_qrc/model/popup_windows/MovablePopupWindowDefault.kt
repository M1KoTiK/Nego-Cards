package m1k.kotik.negocards.data.canvas_qrc.model.popup_windows

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject

abstract class MovablePopupWindowDefault:PopupWindowDefault(), IMovablePopupWindow {
    override fun move(x: Int, y: Int) {
        root!!.update(x,y,-1,-1)

    }

    override fun setup(
        context: Context,
        layoutRes: Int,
        height: Int,
        width: Int,
        isOutsideTouchable: Boolean,
        isFocusable: Boolean
    ) {
        super.setup(context, layoutRes, height, width, isOutsideTouchable, isFocusable)
    }
    abstract fun onPressUp(event: MotionEvent?)
    abstract fun onPressDown(event: MotionEvent?)
    abstract fun onMove(event: MotionEvent?)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate() {
        popupView!!.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    Toast.makeText(context,"nove", Toast.LENGTH_SHORT).show()
                    onPressDown(event)
                }
                MotionEvent.ACTION_MOVE -> {
                    Toast.makeText(context,"nove",Toast.LENGTH_SHORT).show()
                    onMove(event)
                }
                MotionEvent.ACTION_UP -> {
                    Toast.makeText(context,"nove",Toast.LENGTH_SHORT).show()
                    onPressUp(event)
                }
            }
            v?.onTouchEvent(event) ?: true
        }
    }
}








