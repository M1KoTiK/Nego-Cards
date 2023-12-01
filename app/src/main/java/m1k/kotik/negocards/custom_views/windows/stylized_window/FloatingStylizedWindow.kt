package m1k.kotik.negocards.custom_views.windows.stylized_window

import android.annotation.SuppressLint
import android.content.Context
import android.view.*
import android.widget.LinearLayout
import m1k.kotik.negocards.R
import java.util.*


class FloatingStylizedWindow(
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
        // Using kotlin extension for views caused error, so good old findViewById is used
        rootView.findViewById<View>(R.id.window_close).setOnClickListener {
            close()
            rootView.clearFocus()
            hideKeyboard()
        }
        rootView.findViewById<LinearLayout>(R.id.window_header).setOnTouchListener { v, event ->
            val deltaX = event.rawX - startX
            val deltaY= event.rawY - startY
            when(event.action){
                MotionEvent.ACTION_DOWN ->{
                    startX = event.rawX.toInt()
                    startY = event.rawY.toInt()
                    if(canClearFocusWhenClickOnHeader) {
                        rootView.clearFocus()
                    }
                    if(canHideKeyboardWhenClickOnHeader) {
                        hideKeyboard()
                    }
                    initialPosX = windowParameters.x
                    initialPosY = windowParameters.y
                }
                MotionEvent.ACTION_MOVE->{
                    val newX = initialPosX + deltaX.toInt()
                    val newY = initialPosY + deltaY.toInt()
                    windowParameters.x = newX
                    windowParameters.y = newY
                }
            }
            update()
            true
        }
    }

}
