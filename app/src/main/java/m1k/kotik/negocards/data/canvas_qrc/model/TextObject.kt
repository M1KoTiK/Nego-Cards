package m1k.kotik.negocards.data.canvas_qrc.model

import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectRShape


class TextObject(
    var text: String,
    var fontSize: Int,
    color: String,
    posX: Int,
    posY: Int
) : CanvasObject(ObjectType.Text(), (fontSize*text.length), fontSize,posX, posY,color) {





}