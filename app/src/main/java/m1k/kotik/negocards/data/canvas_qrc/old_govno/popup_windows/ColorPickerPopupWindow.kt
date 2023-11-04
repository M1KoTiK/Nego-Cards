package m1k.kotik.negocards.data.canvas_qrc.old_govno.popup_windows

import android.content.Context
import android.graphics.Color.HSVToColor
import m1k.kotik.negocards.R
import m1k.kotik.negocards.custom_views.canvas.HueAndSaturationCirclePicker
import m1k.kotik.negocards.custom_views.canvas.ValueSliderPicker

class ColorPickerPopupWindow(var onColorChange: (Int)->Unit): PopupWindowDefault() {
    private var alpha = 255
    fun setup(context: Context,
              height:Int,
              width:Int,
              isOutsideTouchable: Boolean = true,
              isFocusable: Boolean = true){
        super.setup(context,
            R.layout.color_picker_popup,height,width,isOutsideTouchable,isFocusable)
    }
    var circleColorPicker: HueAndSaturationCirclePicker? = null
        private set

    var valuePicker: ValueSliderPicker? = null
        private set

    var HSV = floatArrayOf(0f,0f,1f)

    override fun onCreate() {
        circleColorPicker = this.popupView!!.findViewById(R.id.circleColorPicker)
        circleColorPicker!!.onHueAndSaturationChange = { hue, saturation ->
            HSV[0] = hue
            HSV[1] = saturation
            onColorChange(HSVToColor(alpha,HSV))
        }
       valuePicker = this.popupView!!.findViewById(R.id.valueStripePicker)
        valuePicker!!.onSliderValueChange = { value ->
            HSV[2] = value
            onColorChange(HSVToColor(alpha,HSV))
        }

    }
}