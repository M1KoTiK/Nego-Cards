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
) : DefaultStylizedWindow(context, windowContentLayoutResource) {

    init {
        setTouchProcessingForWindow()
    }

    private var startX: Int = 0
    private var startY: Int = 0
    private var initialPosX = 0
    private var initialPosY = 0

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchProcessingForWindow() {
        rootView.findViewById<View>(R.id.window_close).setOnClickListener { close() }
        rootView.findViewById<LinearLayout>(R.id.window_header).setOnTouchListener { v, event ->
            val deltaX = event.rawX - startX
            val deltaY= event.rawY - startY
            when(event.action){
                MotionEvent.ACTION_DOWN ->{
                    startX = event.rawX.toInt()
                    startY = event.rawY.toInt()
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
