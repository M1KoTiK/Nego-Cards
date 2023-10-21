package m1k.kotik.negocards.data.canvas_qrc.old_govno.popup_windows

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow

abstract class PopupWindowDefault: IPopupWindow {
    var context:Context? = null
    var  root: PopupWindow? = null
    var  popupView: View? = null
    var x:Int? = null
    var y:Int? = null
    var gravity: Int? = null
    var isCreated: Boolean = false
    override fun setup(
        context: Context,
        layoutRes: Int,
        height: Int,
        width: Int,
        isOutsideTouchable: Boolean,
        isFocusable: Boolean) {
        this.context = context
        val newPopupWindow = PopupWindow()
        popupView = inflate(layoutRes)
        newPopupWindow.isOutsideTouchable = isOutsideTouchable
        newPopupWindow.isFocusable = isFocusable
        newPopupWindow.contentView = popupView
        newPopupWindow.height = height
        newPopupWindow.width = width
        root = newPopupWindow
        onCreate()
        isCreated = true
    }
    abstract fun onCreate()
    private fun inflate(layoutRes: Int): View {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return inflater.inflate(layoutRes, null)
    }
    val isOpen:Boolean
        get(){
            if(root == null){
                return false
                println("root = null")
            }
            return this.root!!.isShowing
        }

    override fun show(x: Int, y: Int, gravity: Int) {

        if(root !=null) {
            this.x = x
            this.y = y
            this.gravity = gravity
            root!!.showAtLocation(popupView, gravity, x, y)
        }
    }

    override fun close() {
        if(root !=null){
            this.x = null
            this.y = null
            this.gravity = null
            root!!.dismiss()
        }
    }
}