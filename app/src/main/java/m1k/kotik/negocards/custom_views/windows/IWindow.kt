package m1k.kotik.negocards.custom_views.windows

import android.content.Context
import android.view.WindowManager

interface IWindow {
    val context: Context
    val windowContentLayoutResource: Int
    val windowRootLayoutResource: Int
    val windowParameters: WindowManager.LayoutParams
    fun show(x: Int, y: Int, width: Int, height: Int, gravity: Int)
    fun close()
    fun update()

}