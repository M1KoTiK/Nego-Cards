package m1k.kotik.negocards.custom_views.windows

import android.annotation.SuppressLint
import android.content.Context
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import m1k.kotik.negocards.R
import java.util.*


class FloatingWindow(
    context: Context,
    windowContentLayoutResource: Int
) : DefaultWindow(context, windowContentLayoutResource) {

    init {
        setTouchProcessingForWindow()
    }

    private var startX: Int = 0
    private var startY: Int = 0
    private var initialPosX = 0
    private var initialPosY = 0

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchProcessingForWindow() {
        // Using kotlin extension for views caused error, so good old findViewById is used
        rootView.findViewById<View>(R.id.window_close).setOnClickListener { close() }
        rootView.findViewById<LinearLayout>(R.id.window_header).setOnTouchListener { v, event ->
            val deltaX = event.rawX - startX
            val deltaY= event.rawY - startY
            when(event.action){
                MotionEvent.ACTION_DOWN ->{
                    startX = event.rawX.toInt()
                    startY = event.rawY.toInt()
                    rootView.clearFocus()
                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(rootView.windowToken, 0)
                    initialPosX = windowParameters.x
                    initialPosY = windowParameters.y
                }
                MotionEvent.ACTION_MOVE->{
                windowParameters.x = initialPosX + deltaX.toInt()
                windowParameters.y = initialPosY + deltaY.toInt()
                }
            }
            update()
            true
        }
    }
}
