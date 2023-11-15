package m1k.kotik.negocards.custom_views.floating_windows

import android.content.Context
import androidx.constraintlayout.widget.ConstraintSet.Layout

interface IFloatingWindow {
    val context: Context
    val layoutResource: Int
    var x: Int
    var y: Int
    var width: Int
    var height: Int

    fun show(x: Int, y: Int)
    fun close()
    fun update()

}