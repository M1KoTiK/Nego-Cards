package m1k.kotik.negocards.custom_views.windows

import android.content.Context

interface IWindow {
    val context: Context
    val windowContentLayoutResource: Int
    val windowRootLayoutResource: Int
    fun show(x: Int, y: Int, width: Int, height: Int, gravity: Int)
    fun close()
    fun update()

}