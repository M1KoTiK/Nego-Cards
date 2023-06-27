package m1k.kotik.negocards.data.canvas_qrc.model.popup_windows

import android.content.Context
import android.view.Gravity
import android.view.View

interface IPopupWindow {
    fun setup(context: Context,
              layoutRes: Int,
              height:Int,
              width:Int,
              isOutsideTouchable:Boolean = true,
              isFocusable:Boolean = true)
    fun show(x:Int,y:Int,gravity: Int)
    fun close()
}