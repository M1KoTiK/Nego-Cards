package m1k.kotik.negocards.data.canvas_qrc.model.popup_windows

import android.content.Context
import android.graphics.Canvas
import m1k.kotik.negocards.R
import m1k.kotik.negocards.view.CanvasView

class CanvasViewerPopupWindow :PopupWindowDefault(){
    lateinit var canvas: CanvasView
    fun setup(context: Context,
              height:Int,
              width:Int,
              isOutsideTouchable: Boolean = true,
              isFocusable: Boolean = true){
        super.setup(context,
            R.layout.canvas_viewer,height,width,isOutsideTouchable,isFocusable)
    }
    override fun onCreate() {
       canvas =  popupView!!.findViewById(R.id.canvasView)!!

    }
}