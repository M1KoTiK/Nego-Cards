package m1k.kotik.negocards.custom_views.windows

import android.R.attr
import android.R.attr.animation
import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
import m1k.kotik.negocards.R


class StaticWindow(context: Context, layout: Int): DefaultStylizedWindow(context,layout) {
    init {
        windowParameters.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    }
}
