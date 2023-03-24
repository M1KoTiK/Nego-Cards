package m1k.kotik.negocards.data.canvas_qrc.model

import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectRShape

class CanvasModel(
    val canvasAsShape: RectRShape,
    val canvasObject: MutableList<CanvasObject>
) {
    fun encode(): String{
        var encodeString: String = ""
        for(obj in canvasObject){
           encodeString += obj.encode()
        }
        return encodeString
    }
}
