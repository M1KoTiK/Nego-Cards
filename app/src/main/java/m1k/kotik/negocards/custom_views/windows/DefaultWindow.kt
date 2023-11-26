package m1k.kotik.negocards.custom_views.windows

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import m1k.kotik.negocards.R

abstract class DefaultWindow(
    final override val context: Context,
    final override var windowContentLayoutResource: Int,
    final override val windowRootLayoutResource: Int = R.layout.default_floating_window,
) : IWindow {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val layoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val rootView: View = layoutInflater.inflate(windowRootLayoutResource, null)
    var contentView: View = layoutInflater.inflate(windowContentLayoutResource, null)

    var isInstanceMustRecreated: Boolean = false
    var isWindowOpen = false

    open var windowParameters = WindowManager.LayoutParams(
        0,
        0,
        0,
        0,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION
        } else {
            WindowManager.LayoutParams.TYPE_APPLICATION
        },
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
        PixelFormat.TRANSLUCENT
    )

    private fun attachChildView() {
        rootView.findViewById<LinearLayout>(R.id.window_content).addView(contentView)
        rootView.requestFocus()
    }


    final override fun show(x: Int, y: Int, width: Int, height: Int, gravity: Int) {
        windowParameters.x = x
        windowParameters.y = y
        windowParameters.height = height
        windowParameters.width = width
        windowParameters.gravity = gravity
        if (!isWindowOpen) {
            if (isInstanceMustRecreated) {
                attachChildView()
                windowManager.addView(rootView, windowParameters)
                isWindowOpen = true
            } else {
                contentView = layoutInflater.inflate(windowContentLayoutResource, null)
                attachChildView()
                windowManager.addView(rootView, windowParameters)
                isWindowOpen = true
            }

        }
    }
    final override fun update() {
        windowManager.updateViewLayout(rootView, windowParameters)
    }
    final override fun close() {
        windowManager.removeView(rootView)
        isWindowOpen = false
    }
}