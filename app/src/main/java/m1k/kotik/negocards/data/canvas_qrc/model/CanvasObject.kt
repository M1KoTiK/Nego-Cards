package m1k.kotik.negocards.data.canvas_qrc.model

import android.graphics.*
import android.graphics.Color.parseColor
import android.text.TextWatcher

/*enum class ObjectType {
    Text,
    Shape,
    Image,
    Reference
}
 */



abstract class CanvasObject(
    var typeObj: ObjectType,
    var width: Int,
    var height: Int,
    var posX: Int,
    var posY: Int,
    var color: String
) {
    open fun encode():String{
        var encodeStr: String = ""
        for (txTag in this.typeObj.listTag) {
            if(txTag.getField(this).toDoubleOrNull() != null){
                encodeStr += "${txTag.tag}${txTag.getField(this)}"
            }
            else{
                encodeStr += "${txTag.tag}\"${txTag.getField(this)}\""
            }

        }
        encodeStr = encodeStr.lowercase()
        return encodeStr
    }
    sealed class ObjectType {
        abstract val tag: String
        open var listTag = listOf<Tag>(
            Tag.ObjType(),
            Tag.Width(),
            Tag.Height(),
            Tag.PosX(),
            Tag.PosY(),
            Tag.Color()
        )
        class Text : ObjectType() {
            override val tag: String get() = "tx"
            override var listTag: List<Tag> =
                super.listTag + listOf(Tag.Text(),Tag.FontSize())
        }
        open class Shape: ObjectType(){
            override val tag: String get() = "sh"
            override var listTag: List<Tag> =
                super.listTag + listOf(Tag.ShapeType())
            class RectR: Shape(){
                override  val tag: String get()= "rr"
                override var listTag: List<Tag> =
                    super.listTag + listOf()
            }
            class Quadril: Shape(){
                override val tag: String get() = "qd"
            }
            class Arc: Shape(){
                override val tag: String get() = "ac"
            }
            class Rect: Shape(){
                override val tag: String get() = "rc"
            }
            class Oval: Shape(){
                override val tag: String get() = "ov"
            }
        }
    }
    sealed class Tag{
        abstract val tag: String
        open val obligatory: Boolean = true
        abstract fun setField(canvasObject: CanvasObject, value:Any)
        abstract fun getField(canvasObject: CanvasObject):String
        class ObjType: Tag() {
            override val tag: String get() = "ot"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.typeObj = value as ObjectType
            }
            override fun getField(canvasObject: CanvasObject): String {
                return canvasObject.typeObj.tag
            }
        }
        class Width: Tag(){
            override val tag: String get() = "w"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.width = value.toString().toInt()
            }
            override fun getField(canvasObject: CanvasObject): String {
                return canvasObject.width.toString()
            }
        }
        class Height: Tag(){
            override val tag: String get() = "h"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.height = value.toString().toInt()
            }
            override fun getField(canvasObject: CanvasObject): String {
                return canvasObject.height.toString()
            }
        }
        class PosX: Tag(){
            override val tag: String get() = "x"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.posX = value.toString().toInt()
            }
            override fun getField(canvasObject: CanvasObject): String {
              return canvasObject.posX.toString()
            }
        }
        class PosY: Tag(){
            override val tag: String get() = "y"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.posY = value.toString().toInt()
            }
            override fun getField(canvasObject: CanvasObject): String {
                return canvasObject.posY.toString()
            }
        }
        class Text: Tag(){
            override val tag: String get() = "t"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject as TextObject
                canvasObject.text = value.toString()
            }

            override fun getField(canvasObject: CanvasObject): String {
                canvasObject as TextObject
                return canvasObject.text.toString()
            }
        }
        class Color: Tag(){
            override val tag: String get() = "cl"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.color = value.toString()
            }
            override fun getField(canvasObject: CanvasObject): String {
                return canvasObject.color.toString()
            }
        }
        class FontSize: Tag(){
            override val tag: String
                get() = "fs"

            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject as TextObject
                canvasObject.fontSize = value.toString().toInt()
            }
            override fun getField(canvasObject: CanvasObject): String {
                canvasObject as TextObject
                return canvasObject.fontSize.toString()
            }
        }
        open class ShapeType: Tag(){
            override val tag: String get() = "st"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject as ShapeObject
                canvasObject.shapeType = value as ObjectType.Shape
            }
            override fun getField(canvasObject: CanvasObject): String {
                canvasObject as ShapeObject
                return canvasObject.shapeType.tag.toString()
            }
        }
    }
    companion object{
    /*
        fun decode(encodeString: String):CanvasObject{
            val obj: CanvasObject

            return obj
        }
        */
    }

}