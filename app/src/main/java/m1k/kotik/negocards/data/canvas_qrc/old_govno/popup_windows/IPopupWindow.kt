package m1k.kotik.negocards.data.canvas_qrc.old_govno.popup_windows

import android.content.Context

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