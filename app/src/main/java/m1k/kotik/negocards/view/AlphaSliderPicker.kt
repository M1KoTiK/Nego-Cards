package m1k.kotik.negocards.view

import android.content.Context
import android.util.AttributeSet

class AlphaSliderPicker(context: Context, attrs: AttributeSet) : Slider(context, attrs) {
    init {
        setup(0.1f, 0f,255f)
    }
}