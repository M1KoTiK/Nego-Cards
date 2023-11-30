package m1k.kotik.negocards.custom_views.windows

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.animation.doOnEnd
import m1k.kotik.negocards.R

open class DefaultStylizedWindow(context: Context, layout: Int):DefaultWindow(context,layout) {
    var canHideKeyboardWhenClickOnHeader = false
    var canClearFocusWhenClickOnHeader = false
    init {
        rootView.findViewById<View>(R.id.window_close).setOnClickListener { close() }
        rootView.findViewById<View>(R.id.window_header).setOnClickListener {
            if(canClearFocusWhenClickOnHeader) {
                rootView.clearFocus()
            }
            if(canHideKeyboardWhenClickOnHeader) {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(rootView.windowToken, 0)
            }
        }
        onBeforeShow = {
            rootView.alpha = 0f
            true
        }
        onAfterShow= {
            if (it) {
                startAlpha(rootView)
                startSize(rootView)
            }
        }
        var canClose = true
        onBeforeClose= {
            if (canClose) {
                canClose = false
                val widthAnimator = ObjectAnimator.ofFloat(rootView, "scaleX", 1f, 0f)
                widthAnimator.duration = 600
                widthAnimator.doOnEnd {
                    forcedClose()
                    canClose = true
                }
                widthAnimator.start()

                val heightAnimator = ObjectAnimator.ofFloat(rootView, "scaleY", 1f, 0f)
                heightAnimator.duration = 500
                heightAnimator.start()
            }
            false
        }
    }

    private fun startAlpha(view: View) {
        val alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        alphaAnimator.duration = 300
        alphaAnimator.start()
    }


    private fun startSize(view: View) {
        val widthAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f)
        widthAnimator.duration = 600
        widthAnimator.repeatMode = ValueAnimator.RESTART
        widthAnimator.start()
        val heightAnimator = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f)
        heightAnimator.duration = 500
        heightAnimator.start()
    }
}