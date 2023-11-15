package m1k.kotik.negocards.custom_views.floating_windows

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.DisplayMetrics
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getSystemService
import m1k.kotik.negocards.R
import java.util.*


open class FloatingWindow(
    override val context: Context,
    override val layoutResource: Int
    ) : IFloatingWindow {

    override var x: Int = 0
        set(value) {
            field = value
            windowParams.x = x
            update()
        }
    override var y: Int = 0
        set(value) {
            field = value
            windowParams.y = y
            update()
        }
    override var width: Int = 300
        set(value) {
            field = value
            windowParams.width = width
            update()
        }
    override var height: Int = 100
        set(value) {
            field = value
            windowParams.height = height
            update()
        }

    protected val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    protected val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    protected val rootView = layoutInflater.inflate(R.layout.default_floating_window, null)!!
    protected val windowParams = WindowManager.LayoutParams(
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

    private fun getCurrentDisplayMetrics(): DisplayMetrics {
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        return dm
    }

    private fun calculateSizeAndPosition(
        params: WindowManager.LayoutParams,
        widthInDp: Int,
        heightInDp: Int
    ) {
        val dm = getCurrentDisplayMetrics()
        // We have to set gravity for which the calculated position is relative.
        params.gravity = Gravity.TOP or Gravity.LEFT
        params.width = (widthInDp * dm.density).toInt()
        params.height = (heightInDp * dm.density).toInt()
        params.x = (dm.widthPixels - params.width) / 2
        params.y = (dm.heightPixels - params.height) / 2
    }

    private fun initWindowParams() {
        calculateSizeAndPosition(windowParams, width, height)
    }
    private var startX: Int = 0
    private var startY: Int = 0
    private var initialPosX = 0
    private var initialPosY = 0
    @SuppressLint("ClickableViewAccessibility")
    private fun initWindow() {

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
                    initialPosX = windowParams.x
                    initialPosY = windowParams.y
                }
                MotionEvent.ACTION_MOVE->{
                x = initialPosX + deltaX.toInt()
                y = initialPosY + deltaY.toInt()
                }
            }
            true
        }
        rootView.findViewById<LinearLayout>(R.id.window_content).addView(layoutInflater.inflate(layoutResource, null))
        rootView.requestFocus()
    }

    init {
        initWindowParams()
        initWindow()
    }



    override fun show(x: Int, y: Int) {
        windowManager.addView(rootView, windowParams)
    }

    override fun close() {
        windowManager.removeView(rootView)
    }

    override fun update() {
        windowManager.updateViewLayout(rootView, windowParams)
    }

}
