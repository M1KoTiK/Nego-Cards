package m1k.kotik.negocards.view

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet

class ValueSliderPicker(context: Context, attrs: AttributeSet) : Slider(context, attrs) {
    init{
        super.setup(0.01f, 0f,1f)
        stripeObject.width = 600
        stripeObject.height = 100
        stripePaint = Paint().also {
            it.shader = LinearGradient(this.stripeObject.posX.toFloat(), 0f,
                this.stripeObject.posX + this.stripeObject.width.toFloat(), 0f,
                0xFFFFFFFF.toInt(),0xFF000000.toInt(),
                Shader.TileMode.REPEAT)
        }
    }
}