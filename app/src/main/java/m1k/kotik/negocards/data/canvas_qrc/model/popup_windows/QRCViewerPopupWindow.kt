package m1k.kotik.negocards.data.canvas_qrc.model.popup_windows

import android.content.Context
import android.widget.ImageView
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.QRC.QRCreator
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.QRCDecoder

class QRCViewerPopupWindow: PopupWindowDefault() {
    lateinit var imageQRC: ImageView
    var qrcCreator = QRCreator()
    var code = String()
    set(value) {
        if(isCreated && value != "")
            println("value = $value")
            imageQRC.setImageBitmap(qrcCreator.getQRCToBitmap(value))
            field = value
    }
    override fun onCreate() {
       imageQRC = popupView!!.findViewById(R.id.QRCImage)
    }

    fun setup(context: Context,
              height:Int,
              width:Int,
              isOutsideTouchable: Boolean = true,
              isFocusable: Boolean = true){
        super.setup(context,
            R.layout.qrc_viewer_popup,height,width,isOutsideTouchable,isFocusable)
    }



}