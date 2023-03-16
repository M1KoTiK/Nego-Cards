package m1k.kotik.negocards.data.canvas_qrc.model

import android.graphics.*
import android.graphics.Color.parseColor
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.Arc

abstract class CanvasObject(
    var typeObj: ObjectType ,
    var width: Int,
    var height: Int,
    var posX: Int,
    var posY: Int,
    var color: String,
    var style: Tag.Style
) {
    abstract fun draw(canvas: Canvas)
    fun getParseColor(): Int {
        return parseColor("#$color")
    }
    open fun encode():String{
        var encodeStr: String = ""
        for (txTag in this.typeObj.listTag) {
            var field = txTag.getField(this)
            if(field.toString() == txTag.default.toString()){
                encodeStr += "${txTag.tag}\"\""
            }
            else{
                if(field.toString().toDoubleOrNull() != null){
                    encodeStr += "${txTag.tag}${field.toString()}"
                }
                else{
                    encodeStr += "${txTag.tag}\"${field.toString()}\""
                }
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
            class RectR: Shape() { override  val tag: String get()= "rr" }
            class Quadril: Shape() { override val tag: String get() = "qd" }
            class Arc: Shape() { override val tag: String get() = "ac" }
            class Rect: Shape(){ override val tag: String get() = "rc" }
            class Oval: Shape(){ override val tag: String get() = "ov" }
        }
    }
    sealed class Tag{
        abstract val tag: String
        abstract val default: String
        open val obligatory: Boolean = true
        abstract fun setField(canvasObject: CanvasObject, value:Any)
        abstract fun getField(canvasObject: CanvasObject):Any

        class ObjType: Tag() {
            override val default: String get() = ObjectType.Text().tag
            override val tag: String get() = "ot"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.typeObj = value as ObjectType
            }
            override fun getField(canvasObject: CanvasObject): String {
                return canvasObject.typeObj.tag
            }
        }

        class Width: Tag(){
            override val default: String get() = "0"
            override val tag: String get() = "w"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.width = value.toString().toInt()
            }
            override fun getField(canvasObject: CanvasObject): String {
                return canvasObject.width.toString()
            }
        }

        class Height: Tag(){
            override val default: String get() = "0"
            override val tag: String get() = "h"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.height = value.toString().toInt()
            }
            override fun getField(canvasObject: CanvasObject): Any {
                return canvasObject.height.toString()
            }
        }

        class PosX: Tag(){
            override val default: String get() = "0"
            override val tag: String get() = "x"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.posX = value.toString().toInt()
            }
            override fun getField(canvasObject: CanvasObject): String {
              return canvasObject.posX.toString()
            }
        }

        class PosY: Tag(){
            override val default: String get() = "0"
            override val tag: String get() = "y"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.posY = value.toString().toInt()
            }
            override fun getField(canvasObject: CanvasObject): String {
                return canvasObject.posY.toString()
            }
        }

        class Text: Tag(){
            override val default: String get() = "text"
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
            override val default: String get() = "FF181818"
            override val tag: String get() = "cl"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.color = value.toString()
            }
            override fun getField(canvasObject: CanvasObject): String {
                return canvasObject.color.toString()
            }
        }

        class FontSize: Tag(){
            override val default: String get() = "16"
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
            override val default: String get() = ObjectType.Shape.RectR().tag
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

        class StartAngle : Tag(){
            override val default: String get() = "45"
            override val tag: String
                get() = "sg"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject as Arc
                canvasObject.startAngle = value.toString().toInt()
            }
            override fun getField(canvasObject: CanvasObject): String {
                canvasObject as Arc
                return  canvasObject.startAngle.toString()
            }

        }

        class SweepAngle : Tag() {
            override val default: String get() = "270"
            override val tag: String
                get() = "sa"

            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject as Arc
                canvasObject.sweepAngle = value.toString().toInt()
            }

            override fun getField(canvasObject: CanvasObject): String {
                canvasObject as Arc
                return canvasObject.sweepAngle.toString()
            }
        }

        open class Style :Tag() {
            override val tag: String
                get() = "s"
            override val default: String get() = Style.Fill().tag
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.style = value as Style
            }
            override fun getField(canvasObject: CanvasObject): Any {
                return canvasObject.style.tag
            }
            class Fill : Style() { override val tag: String get() = "sf" }
            class Stroke : Style() { override val tag: String get() = "ss" }
        }
    }
}
