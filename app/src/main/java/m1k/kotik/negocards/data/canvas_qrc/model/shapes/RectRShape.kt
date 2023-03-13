package m1k.kotik.negocards.data.canvas_qrc.model.shapes

import android.graphics.Color
import android.graphics.Paint
import m1k.kotik.negocards.data.canvas_qrc.model.ShapeObject
import m1k.kotik.negocards.data.canvas_qrc.model.ShapeType


class RectRShape (
    val leftCorner: Int,
    val rightCorner: Int,

    posX: Int = 0,
    posY: Int = 0,
    width: Int = 0,
    height: Int = 0,
    color: String = "FF181818",
    style: Paint.Style
) : ShapeObject(ShapeType.RectRound, posX, posY, width, height,color,style) {
    override fun encode(): String {
        var encodeStr: String = ""
        val shapeTag = enumValues<RectRShapeValueTag>()
        for (shTag in shapeTag) {
            if(shTag.shField(this).toDoubleOrNull() != null){
                encodeStr += "${shTag.tag}${shTag.shField(this)}"
            }
            else{
                encodeStr += "${shTag.tag}\"${shTag.shField(this)}\""
            }

        }
        encodeStr = encodeStr.lowercase()
        return encodeStr
    }

    enum class RectRShapeValueTag (val tag: String,val obligatory: Boolean = true) {
        ShapeType("st"){
            override fun shField(shObj: RectRShape?): String {
                return shObj?.shapeType?.tag.toString()
            }
        },
        Color("cl") {
            override fun shField(shObj: RectRShape?): String {
                return shObj?.color.toString()
            }
        },
        Style("sl") {
            override fun shField(shObj: RectRShape?): String {
                return shObj?.style.toString()
            }
        },
        PosX("x") {
            override fun shField(shObj: RectRShape?): String {
                return shObj?.posX.toString()
            }
        },
        PosY("y") {
            override fun shField(shObj: RectRShape?): String {
                return shObj?.posY.toString()
            }
        },
        Width("w") {
            override fun shField(shObj: RectRShape?): String {
                return shObj?.width.toString()
            }
        },
        Height("h") {
            override fun shField(shObj: RectRShape?): String {
                return shObj?.height.toString()
            }
        },
        LeftCorner("lc") {
            override fun shField(shObj: RectRShape?): String {
                return shObj?.leftCorner.toString()
            }
        },
        RightCorner("rc") {
            override fun shField(shObj: RectRShape?): String {
                return shObj?.rightCorner.toString()
            }
        };

        abstract fun shField(shObj: RectRShape?): String
    }
}