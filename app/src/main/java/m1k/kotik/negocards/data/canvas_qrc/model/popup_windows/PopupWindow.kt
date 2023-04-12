package m1k.kotik.negocards.data.canvas_qrc.model.popup_windows

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow

class PopupWindow: IPopupWindow {
    var  root: PopupWindow? = null
    var  popupView: View? = null
    override fun setup(
        context: Context,
        layoutRes: Int,
        height: Int,
        width: Int,
        isOutsideTouchable: Boolean,
        isFocusable: Boolean) {
        val newPopupWindow = PopupWindow()
        popupView = inflate(context,layoutRes)
        newPopupWindow.isOutsideTouchable = isOutsideTouchable
        newPopupWindow.isFocusable = isFocusable
        newPopupWindow.contentView = popupView
        newPopupWindow.height = height
        newPopupWindow.width = width
        root = newPopupWindow
    }
    private fun inflate(context: Context, layoutRes: Int): View {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return inflater.inflate(layoutRes, null)
    }

    override fun show(x: Int, y: Int, gravity: Int) {
        if(root !=null) {
            root!!.showAtLocation(popupView, gravity, x, y)
        }
    }

    override fun close() {
        if(root !=null){
            root!!.dismiss()
        }
    }
}