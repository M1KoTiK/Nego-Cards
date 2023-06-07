package m1k.kotik.negocards.data.canvas_qrc.model.popup_windows

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import m1k.kotik.negocards.R


open class InputTextPopupWindow : PopupWindowDefault() {
    lateinit var inpText : EditText
    lateinit var copyAndPasteMenuPopupWindow: CopyAndPasteMenuPopupWindow
    var onInpTextChange: ()->Unit= {}
    var initText:String = ""
    override fun onCreate() {
        //inpText.setText(initText)
        inpText = this.popupView!!.findViewById<EditText>(R.id.editInpText)
        copyAndPasteMenuPopupWindow = CopyAndPasteMenuPopupWindow(inpText)
        copyAndPasteMenuPopupWindow.setup(super.context!!)
        inpText.setOnLongClickListener {
            println("longCLick")
            copyAndPasteMenuPopupWindow.show()
            true
        }
        inpText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                onInpTextChange.invoke()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

    }

    override fun show(x: Int, y: Int, gravity: Int) {
        if(!root!!.isShowing){
            inpText.setText(initText)
            super.show(x, y, gravity)
        }
    }
    fun setup(
        context: Context,
        height: Int,
        width: Int,
        isOutsideTouchable: Boolean = true,
        isFocusable: Boolean = true,
    ){
        super.setup(context,
            R.layout.input_text_popup,height,width,isOutsideTouchable,isFocusable)
    }
}