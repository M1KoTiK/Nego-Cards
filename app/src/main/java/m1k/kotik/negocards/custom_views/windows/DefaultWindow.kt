package m1k.kotik.negocards.custom_views.windows

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.*
import android.view.View.OnKeyListener
import android.widget.LinearLayout
import androidx.core.content.getSystemService
import androidx.core.view.children
import m1k.kotik.negocards.R
import m1k.kotik.negocards.custom_views.layouts.BackPressNotifyingLinearLayout

abstract class DefaultWindow(
    final override val context: Context,
    final override var windowContentLayoutResource: Int,
    final override val windowRootLayoutResource: Int = R.layout.default_floating_window,
) : IWindow {
    val windowManager = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
    private val layoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val rootView: View = layoutInflater.inflate(windowRootLayoutResource, null)
    var contentView: View = layoutInflater.inflate(windowContentLayoutResource, rootView.findViewById(R.id.window_content))

    var isInstanceMustRecreated: Boolean = false
    var isWindowOpen = false
    private set

    private var isContentAttached = false
    override var windowParameters = WindowManager.LayoutParams(
        0,
        0,
        0,
        0,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION
        } else {
            WindowManager.LayoutParams.TYPE_APPLICATION
        },
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
        PixelFormat.TRANSLUCENT
    )


    private fun attachChildView() {
        rootView.findViewById<LinearLayout>(R.id.window_content).also {
            it.addView(contentView)
        }
        rootView.requestFocus()

    }
    private fun detachChildView() {
        rootView.findViewById<ViewGroup>(R.id.window_content).removeAllViews()
    }

    final override fun show(x: Int, y: Int, width: Int, height: Int, gravity: Int) {
        windowParameters.x = x
        windowParameters.y = y
        windowParameters.height = height
        windowParameters.width = width
        windowParameters.gravity = gravity
        if(onBeforeShow()) {
            if (!isWindowOpen) {
                if (isInstanceMustRecreated) {
                    isContentAttached = true
                    windowManager.addView(rootView, windowParameters)
                    if(isContentAttached){
                        detachChildView()
                    }
                    attachChildView()
                    isContentAttached = true
                    isWindowOpen = true
                } else {
                    contentView = layoutInflater.inflate(windowContentLayoutResource, null)
                    windowManager.addView(rootView, windowParameters)
                    if(!isContentAttached){
                        attachChildView()
                    }
                    isContentAttached = true
                    isWindowOpen = true
                }
                onAfterShow(true)
                return
            }
        }
        onAfterShow(false)
    }

    protected var onBeforeClose: ()->Boolean = {true}
    protected var onAfterClose: ()->Unit = {}
    protected var onBeforeShow: ()->Boolean = {true}
    protected var onAfterShow : (isSuccess: Boolean)->Unit = {}

    final override fun update() {
        windowManager.updateViewLayout(rootView, windowParameters)
    }
    protected fun forcedClose(){
        if(isWindowOpen) {
            windowManager.removeView(rootView)
            isWindowOpen = false
        }
    }

    final override fun close() {
        if(onBeforeClose()) {
            if(isWindowOpen) {
                windowManager.removeView(rootView)
                isWindowOpen = false
            }
        }
        onAfterClose()
    }
}