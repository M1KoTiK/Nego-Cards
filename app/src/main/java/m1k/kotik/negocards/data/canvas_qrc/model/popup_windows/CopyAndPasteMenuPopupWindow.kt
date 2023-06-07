package m1k.kotik.negocards.data.canvas_qrc.model.popup_windows

import android.content.Context
import android.util.DisplayMetrics
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import m1k.kotik.negocards.R


class CopyAndPasteMenuPopupWindow(var editText: EditText): PopupWindowDefault() {
    private lateinit var copyButton: Button
    private lateinit var pasteButton: Button
    private lateinit var selectAllButton: Button
    override fun onCreate() {
        copyButton = super.popupView!!.findViewById(R.id.copy)
        pasteButton = super.popupView!!.findViewById(R.id.paste)
        selectAllButton = super.popupView!!.findViewById(R.id.selectAll)
        copyButton.setOnClickListener {

        }
        pasteButton.setOnClickListener {

        }
        selectAllButton.setOnClickListener{

        }
    }

    fun setup(context: Context){
       // val displaymetrics: DisplayMetrics = super.context!!.applicationContext!!.resources.displayMetrics
        super.setup(context, R.layout.copy_and_paste_menu, 160,1200,
            isOutsideTouchable = true,
            isFocusable = false)
    }
    fun show(){
        val location = IntArray(2)
        editText.getLocationOnScreen(location)
        val x = location[0]
        val y = location[1]
        super.show(x-200,y-180,Gravity.CENTER_HORIZONTAL and Gravity.CENTER_VERTICAL)
    }
}