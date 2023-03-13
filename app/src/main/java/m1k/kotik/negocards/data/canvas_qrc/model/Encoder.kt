package m1k.kotik.negocards.data.canvas_qrc.model

import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectRShape

class Encoder(
    val canvasAsShape: RectRShape,
    val canvasObject: MutableList<CanvasObject>
) {
    fun encode(): String{
        var encodeString: String = ""
        encodeString = "type:canvas:"
        return encodeString
    }
}