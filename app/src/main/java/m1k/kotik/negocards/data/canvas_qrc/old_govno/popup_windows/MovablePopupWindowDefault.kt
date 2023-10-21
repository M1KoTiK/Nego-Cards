package m1k.kotik.negocards.data.canvas_qrc.old_govno.popup_windows

import android.content.Context
import android.view.MotionEvent

abstract class MovablePopupWindowDefault: PopupWindowDefault(), IMovablePopupWindow {
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
    override fun onCreate() {

    }
}








