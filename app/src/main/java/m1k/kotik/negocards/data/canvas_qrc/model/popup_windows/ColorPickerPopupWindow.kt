package m1k.kotik.negocards.data.canvas_qrc.model.popup_windows

import android.content.Context
import android.view.MotionEvent
import android.widget.Toast
import m1k.kotik.negocards.R

class ColorPickerPopupWindow: MovablePopupWindowDefault() {
    fun setup(context: Context,
              height:Int,
              width:Int,
              isOutsideTouchable: Boolean = true,
              isFocusable: Boolean = true){
        super.setup(context,
            R.layout.color_picker_popup,height,width,isOutsideTouchable,isFocusable)
    }

    override fun onMove(event: MotionEvent?) {
        move(event!!.x.toInt(),event.y.toInt())
        Toast.makeText(context,"move",Toast.LENGTH_SHORT).show()
    }

    override fun onPressUp(event: MotionEvent?) {
    }

    override fun onPressDown(event: MotionEvent?) {

    }

    override fun onCreate() {

    }
}