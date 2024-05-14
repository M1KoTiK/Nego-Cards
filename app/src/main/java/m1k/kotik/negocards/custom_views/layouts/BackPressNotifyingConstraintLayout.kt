package m1k.kotik.negocards.custom_views.layouts

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout

class BackPressNotifyingConstraintLayout(context: Context, attributeSet: AttributeSet): ConstraintLayout(context, attributeSet) {
    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        event ?: return false
        if (event.keyCode == KeyEvent.KEYCODE_BACK
            || event.keyCode == KeyEvent.KEYCODE_SETTINGS) {
            if (event.action == KeyEvent.ACTION_DOWN) {
                onBackPressed()
            }
        }
        return true
    }
    var onBackPressed : ()->Unit = {}
}