package m1k.kotik.negocards.custom_views.canvas

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet

class SliderDefault(context: Context, attrs: AttributeSet) : Slider(context, attrs) {
    init {
        setup(0.01f, 0f,1f)
        stripeObject.width = 600
        stripeObject.height = 100
        stripePaint = Paint().also {
            it.color = Color.WHITE
        }
    }
}