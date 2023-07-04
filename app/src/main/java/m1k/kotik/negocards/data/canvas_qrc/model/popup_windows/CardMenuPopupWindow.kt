package m1k.kotik.negocards.data.canvas_qrc.model.popup_windows

import android.view.Gravity
import android.widget.Button
import m1k.kotik.negocards.R

class CardMenuPopupWindow : PopupWindowDefault() {


    var onClickSaveAsImageButton: ()->Unit = {}
    var onClickSaveAsQRC:()->Unit= {}
    override fun onCreate() {
        popupView!!.findViewById<Button>(R.id.button7).setOnClickListener { onClickSaveAsImageButton.invoke() }
        popupView!!.findViewById<Button>(R.id.button6).setOnClickListener { onClickSaveAsQRC.invoke()}
    }
}