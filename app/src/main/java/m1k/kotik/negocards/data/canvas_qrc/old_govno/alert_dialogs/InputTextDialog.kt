package m1k.kotik.negocards.data.canvas_qrc.old_govno.alert_dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import m1k.kotik.negocards.R


@SuppressLint("ResourceType")
class InputTextDialog(context: Context): Dialog(context,R.style.MaterialDialogSheet) {
    lateinit var inpText : EditText
    var onInpTextChange: ()->Unit= {}
    var initText:String = ""

   init{
       window!!.setGravity(Gravity.BOTTOM);
       val wmlp: WindowManager.LayoutParams = window!!.attributes
       wmlp.gravity = Gravity.CENTER_HORIZONTAL
       wmlp.y = 80 //x position

       window!!.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,
           LinearLayout.LayoutParams.WRAP_CONTENT);
       setContentView(R.layout.input_text_popup)
       setCanceledOnTouchOutside(true)

       inpText = this.findViewById<EditText>(R.id.editInpText)
       inpText.addTextChangedListener(object : TextWatcher {
           override fun afterTextChanged(s: Editable) {
               onInpTextChange.invoke()
           }
           override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
           override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
       })
   }


    override fun show() {
        inpText.setText(initText)
        super.show()
    }


}