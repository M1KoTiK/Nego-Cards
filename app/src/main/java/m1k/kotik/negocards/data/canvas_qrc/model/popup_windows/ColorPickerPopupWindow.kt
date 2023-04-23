package m1k.kotik.negocards.data.canvas_qrc.model.popup_windows

import android.content.Context
import android.graphics.Color.HSVToColor
import android.widget.Toast
import m1k.kotik.negocards.R
import m1k.kotik.negocards.view.HueAndSaturationCirclePicker

class ColorPickerPopupWindow(var onColorChange: (Int)->Unit): PopupWindowDefault() {

    fun setup(context: Context,
              height:Int,
              width:Int,
              isOutsideTouchable: Boolean = true,
              isFocusable: Boolean = true){
        super.setup(context,
            R.layout.color_picker_popup,height,width,isOutsideTouchable,isFocusable)
    }
    var circleColorPicker:HueAndSaturationCirclePicker? = null
        private set
    var HSV = floatArrayOf(0f,0f,1f)

    override fun onCreate() {
        circleColorPicker = this.popupView!!.findViewById(R.id.circleColorPicker)
        circleColorPicker!!.onHueAndSaturationChange = { hue, saturation ->
            HSV[0] = hue.toFloat()
            HSV[1] = saturation.toFloat()
            onColorChange(HSVToColor(255,HSV))
        }
        }



}