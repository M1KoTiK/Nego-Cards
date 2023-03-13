package m1k.kotik.negocards.data.canvas_qrc.model

import android.graphics.Color
import android.graphics.Paint
import java.util.*

enum class ShapeType(val tag: String) {
    Rect("rc"),
    Line("ln"),
    Arc("ar"),
    RectRound("rr"),
    Oval("ov"),
    Quadril("qd")
}

open class ShapeObject(
    val shapeType: ShapeType,
    posX: Int = 0,
    posY: Int = 0,
    width: Int = 0,
    height: Int = 0,
    val color:  String = "FF181818",
    val style: Paint.Style = Paint.Style.FILL,

) : CanvasObject(ObjectType.Shape, width, height, posX, posY) {

    override fun encode(): String {
        var encodeStr: String = ""
        val shapeTag = enumValues<ShapeValueTag>()
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
    enum class ShapeValueTag(val tag: String,val obligatory: Boolean = true) {
        ShapeType("st"){
            override fun shField(shObj: ShapeObject?): String {
                return shObj?.shapeType?.tag.toString()
            }
        },
        Color("cl"){
            override fun shField(shObj: ShapeObject?): String {
                return shObj?.color.toString()
            }
                   },
        Style("sl"){
            override fun shField(shObj: ShapeObject?): String {
                return shObj?.style.toString()
            }
        },
        PosX("x"){
            override fun shField(shObj: ShapeObject?): String {
                return shObj?.posX.toString()
            }
                 },
        PosY("y"){
            override fun shField(shObj: ShapeObject?): String {
                return shObj?.posY.toString()
            }
                 },
        Width("w"){
            override fun shField(shObj: ShapeObject?): String {
                return shObj?.width.toString()
            }
                  },
        Height("h") {
            override fun shField(shObj: ShapeObject?): String {
                return shObj?.height.toString()
            }
        };
        abstract fun shField(shObj: ShapeObject?): String
    }

    override fun decode(encodeString: String): CanvasObject {
        var textTag = ""
        var canvasObject: CanvasObject
        for (char in encodeString){
            while(char!='\"' && char.toString().toDoubleOrNull()==null){
                textTag ++
            }

        }
    }
}