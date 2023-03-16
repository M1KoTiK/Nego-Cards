package m1k.kotik.negocards.data.canvas_qrc.model

import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectR

class CanvasModel(
    val canvasAsShape: RectR,
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
