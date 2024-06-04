package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools

import android.graphics.Canvas
import android.view.MotionEvent
import m1k.kotik.negocards.data.measure_utils.isClickOnObject

abstract class TouchProcessingCanvasTool<T>: ICanvasTool<T> {
    var isPressed: Boolean = false
        private set

    open val onClickDown: (x:Float, y:Float) -> Unit = { _, _ ->}
    open val onClickUp: (x:Float, y:Float) -> Unit = { _, _ ->}
    open val onMovingWhenPressed: (deltaX: Float,
                              deltaY: Float,
                              x: Float,
                              y: Float) -> Unit = { _, _, _, _ ->}

    open val onDoubleClick: (x:Float, y:Float) -> Unit = { _, _ ->}
    open var doubleClickTime: Int = 100

    open val onLongClick: (x:Float, y:Float) -> Unit = { _, _ ->}
    open var longClickTime: Int = 500

    private var startX: Int = 0
    private var startY: Int = 0

    final override val onTouchEvent: (event: MotionEvent) -> Boolean ={
        if(objectsForEdit.isNotEmpty()) {
            val deltaX = it.x - startX
            val deltaY = it.y - startY
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = it.x.toInt()
                    startY = it.y.toInt()
                    if (isClickOnObject(
                            this.x, this.y, this.width, this.height,
                            it.x.toInt(), it.y.toInt()
                        )
                    ) {
                        isPressed = true
                        onClickDown(it.x, it.y)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    isPressed = false
                    if (isClickOnObject(
                            this.x, this.y, this.width, this.height,
                            it.x.toInt(), it.y.toInt()
                        )
                    ) {
                        onClickUp(it.x, it.y)
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    if (isPressed) {
                        onMovingWhenPressed(deltaX, deltaY, it.x, it.y)
                    }
                }
            }
        }
        isPressed
    }

}