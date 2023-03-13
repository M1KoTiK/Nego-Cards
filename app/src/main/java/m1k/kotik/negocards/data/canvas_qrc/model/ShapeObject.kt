package m1k.kotik.negocards.data.canvas_qrc.model

import android.graphics.Color
import android.graphics.Paint
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
    val color:  Int = Color.BLACK,
    val style: Paint.Style = Paint.Style.FILL,

) : CanvasObject(ObjectType.Shape, width, height, posX, posY) {

    override fun encode(): String{
        var encodeStr: String = ""
        val shapeTag = enumValues<ShapeValueTag>()
        for(shTag in shapeTag){
            encodeStr += "${shTag.tag}:${shTag.shField(this)}"
        }
        return encodeStr

 }
    enum class ShapeValueTag(val tag: String) {
        ShapeType("st"){
            override fun shField(shObj: ShapeObject?): String {
                return shObj?.shapeType.toString()
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
}