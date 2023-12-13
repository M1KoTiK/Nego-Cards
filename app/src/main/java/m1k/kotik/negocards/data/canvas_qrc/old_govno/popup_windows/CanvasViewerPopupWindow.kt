package m1k.kotik.negocards.data.canvas_qrc.old_govno.popup_windows

import android.content.Context
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.old_govno.CanvasView

class CanvasViewerPopupWindow :PopupWindowDefault(){
    lateinit var canvas: CanvasView
    fun setup(context: Context,
              height:Int,
              width:Int,
              isOutsideTouchable: Boolean = true,
              isFocusable: Boolean = true){
        super.setup(context,
            R.layout.fragment_canvas_viewer,height,width,isOutsideTouchable,isFocusable)
    }
    override fun onCreate() {
       //canvas =  popupView!!.findViewById(R.id.canvasView)!!

    }
}