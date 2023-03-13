package m1k.kotik.negocards.data.canvas_qrc.model

import android.graphics.Color
import android.graphics.fonts.Font


class TextObject(
    val text: String,
    val color: Int,
    val fontSize: Int,
    posX: Int,
    posY: Int
) : CanvasObject(ObjectType.Text, 0, 0, posX, posY) {
    override fun encode(): String {
        TODO("Not yet implemented")
    }

}