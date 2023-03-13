package m1k.kotik.negocards.data.canvas_qrc.model

import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectRShape


class TextObject(
    val text: String,
    val color: String = "FF181818",
    val fontSize: Int,
    posX: Int,
    posY: Int
) : CanvasObject(ObjectType.Text, fontSize, fontSize*text.length, posX, posY) {

    override fun encode(): String {
        var encodeStr: String = ""
        val textTag = enumValues<TextValueTag>()
        for (txTag in textTag) {
            if(txTag.shField(this).toDoubleOrNull() != null){
                encodeStr += "${txTag.tag}${txTag.shField(this)}"
            }
            else{
                encodeStr += "${txTag.tag}\"${txTag.shField(this)}\""
            }

        }
        encodeStr = encodeStr.lowercase()
        return encodeStr
    }

    override fun decode(encodeString: String): CanvasObject {
        TODO("Not yet implemented")
    }

    enum class TextValueTag (val tag: String,val obligatory: Boolean = true) {
        Text("t"){
            override fun shField(txObj: TextObject?): String {
                return txObj?.text.toString()
            }
        },
        Color("cl") {
            override fun shField(txObj: TextObject?): String {
                return txObj?.color.toString()
            }
        },
        FontSize("fs") {
            override fun shField(txObj: TextObject?): String {
                return txObj?.posX.toString()
            }
        },
        PosX("x") {
            override fun shField(txObj: TextObject?): String {
                return txObj?.posX.toString()
            }
        },
        PosY("y") {
            override fun shField(txObj: TextObject?): String {
                return txObj?.posY.toString()
            }
        },
        Width("w") {
            override fun shField(txObj: TextObject?): String {
                return txObj?.width.toString()
            }
        },
        Height("h") {
            override fun shField(txObj: TextObject?): String {
                return txObj?.height.toString()
            }
        };

        abstract fun shField(txObj: TextObject?): String
    }
}