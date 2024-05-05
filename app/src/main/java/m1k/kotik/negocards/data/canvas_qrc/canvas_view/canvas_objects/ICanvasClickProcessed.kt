package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects

import android.view.MotionEvent

interface ICanvasClickProcessed {
    var onTouchEvent: (event: MotionEvent)-> Boolean
}
