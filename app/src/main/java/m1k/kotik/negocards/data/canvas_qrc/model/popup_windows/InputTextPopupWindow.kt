package m1k.kotik.negocards.data.canvas_qrc.model.popup_windows

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import m1k.kotik.negocards.R


class InputTextPopupWindow : PopupWindowDefault() {
    lateinit var inpText : EditText
    var onInpTextChange: ()->Unit= {}
    var initText:String = ""
    override fun onCreate() {
        //inpText.setText(initText)
        inpText = this.popupView!!.findViewById<EditText>(R.id.editInpText)
        inpText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                onInpTextChange.invoke()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    override fun show(x: Int, y: Int, gravity: Int) {
        inpText.setText(initText)
        super.show(x, y, gravity)

    }
    fun setup(context: Context,
              height:Int,
              width:Int,
              isOutsideTouchable: Boolean = true,
              isFocusable: Boolean = true){
        super.setup(context,
            R.layout.input_text_popup,height,width,isOutsideTouchable,isFocusable)
    }
}