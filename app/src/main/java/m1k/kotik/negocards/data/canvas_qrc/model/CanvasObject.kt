package m1k.kotik.negocards.data.canvas_qrc.model

import android.graphics.*
import android.graphics.Color.parseColor
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.ArcShape
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectRShape

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
    open fun encode():String{
        var code: String = ""
        for (objTag in this.typeObj.listTag) {
            var field = objTag.getField(this)
            if(field.toString() == objTag.default.toString()){
                code += "${objTag.tag}\"\""
            }
            else{
                if(field.toString().toDoubleOrNull() != null){
                    code += "${objTag.tag}${field.toString()}"
                }
                else{
                    code += "${objTag.tag}\"${field.toString()}\""
                }
            }
        }
        return code
    }
    open fun applyCode(code: String){
        var tagToValue: MutableMap<String,Any>
        tagToValue = SplitValuesAndTags(code)
        for(tag in this.typeObj.listTag){
            if(tagToValue.containsKey(tag.tag)){
                tag.setField(this, tagToValue[tag.tag]!!)
            }
        }


        // локальные функции


        // начало основной функции

    }
//------------------------------------
// классы типов объектов
//------------------------------------
    sealed class ObjectType {
        abstract val tag: String
        abstract val classType: CanvasObject
        open val listTag =listOf(
        Tag.ObjType(),
        Tag.Width(),
        Tag.Height(),
        Tag.PosX(),
        Tag.PosY(),
        Tag.Color(),
        Tag.Style())

        class Text: ObjectType() {
            override val tag: String get() = "tx"
            override val classType: CanvasObject get() = TextObject()
            override val listTag: List<Tag>
                get() = super.listTag + listOf(Tag.Text(),Tag.FontSize())
        }
        open class Shape: ObjectType(){
            override val tag: String get() = "sh"
            override val classType: CanvasObject get() = ShapeObject()
            override var listTag: List<Tag> =
                super.listTag + listOf(Tag.ShapeType())

            class RectR: Shape() {
                override  val tag: String get()= "rr"
                override val classType: CanvasObject get() = RectRShape()
                override var listTag: List<Tag> =
                    super.listTag + listOf(Tag.LeftCorner(),Tag.RightCorner())
            }
            class Quadril: Shape() {
                override val tag: String get() = "qd"
            }
            class Arc: Shape() { override val tag: String get() = "ac" }
            class Rect: Shape(){ override val tag: String get() = "rc" }
            class Oval: Shape(){ override val tag: String get() = "ov" }
        }
    }

//------------------------------
// Классы тегов
//------------------------------
    sealed class Tag{
        abstract val tag: String
        abstract val default: Any
        open val obligatory: Boolean = true
        abstract fun setField(canvasObject: CanvasObject, value:Any)
        abstract fun getField(canvasObject: CanvasObject):Any

        class ObjType: Tag() {
            override val default: ObjectType get() = ObjectType.Text()
            override val tag: String get() = "ot"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.typeObj = value as ObjectType
            }
            override fun getField(canvasObject: CanvasObject): String {
                return canvasObject.typeObj.tag
            }
        }

        class Width: Tag(){
            override val default: Int get() = 0
            override val tag: String get() = "w"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.width = value.toString().toInt()
            }
            override fun getField(canvasObject: CanvasObject): String {
                return canvasObject.width.toString()
            }
        }

        class Height: Tag(){
            override val default: Int get() = 0
            override val tag: String get() = "h"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.height = value.toString().toInt()
            }
            override fun getField(canvasObject: CanvasObject): Any {
                return canvasObject.height.toString()
            }
        }

        class PosX: Tag(){
            override val default: Int get() = 0
            override val tag: String get() = "x"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.posX = value.toString().toInt()
            }
            override fun getField(canvasObject: CanvasObject): String {
              return canvasObject.posX.toString()
            }
        }

        class PosY: Tag(){
            override val default: Int get() = 0
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
            override val default: Int get() = 16
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
            override val default: ObjectType.Shape get() = ObjectType.Shape.RectR()
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
            override val default: Int get() = 45
            override val tag: String
                get() = "sg"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject as ArcShape
                canvasObject.startAngle = value.toString().toInt()
            }
            override fun getField(canvasObject: CanvasObject): String {
                canvasObject as ArcShape
                return  canvasObject.startAngle.toString()
            }

        }

        class SweepAngle : Tag() {
            override val default: Int get() = 270
            override val tag: String
                get() = "sa"

            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject as ArcShape
                canvasObject.sweepAngle = value.toString().toInt()
            }

            override fun getField(canvasObject: CanvasObject): String {
                canvasObject as ArcShape
                return canvasObject.sweepAngle.toString()
            }
        }
        class LeftCorner: Tag(){
            override val tag: String
                get() = "lc"
            override val default: String
                get() = "15"

            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject as RectRShape
                canvasObject.leftCorner = value.toString().toInt()
            }

            override fun getField(canvasObject: CanvasObject): Any {
                canvasObject as RectRShape
                return canvasObject.leftCorner.toString()
            }

        }
        class RightCorner: Tag(){
            override val tag: String
                get() = "rc"
            override val default: String
                get() = "15"

            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject as RectRShape
                canvasObject.rightCorner = value.toString().toInt()
            }

            override fun getField(canvasObject: CanvasObject): Any {
                canvasObject as RectRShape
                return canvasObject.rightCorner.toString()
            }

        }

        open class Style :Tag() {
            open val sType: Paint.Style = Paint.Style.FILL
            override val tag: String
                get() = "s"
            override val default: String get() = Style.Fill().tag
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.style = value as Style
            }
            override fun getField(canvasObject: CanvasObject): Any {
                return canvasObject.style.tag
            }
            class Fill : Style() {
                override val tag: String get() = "sf"
                override val sType: Paint.Style = Paint.Style.FILL

            }
            class Stroke : Style() {
                override val tag: String get() = "ss"
                override val sType: Paint.Style = Paint.Style.STROKE

            }
        }
    }

//Дополнительный функционал
    fun getParseColor(): Int {
        return parseColor("#$color")
    }


    companion object{
        const val STR_CONTAINS = "\""
        const val SEPARATOR_SYMBOL = ';'
        var searchableListObjectType = listOf<ObjectType>(
            ObjectType.Text(),
            ObjectType.Shape(),
            ObjectType.Shape.RectR(),
            ObjectType.Shape.Arc(),
            ObjectType.Shape.Rect(),
            ObjectType.Shape.Oval()
        )
        fun SplitValuesAndTags(code: String):MutableMap<String,Any>{
            var index: Int = 0
            var tagToValue = mutableMapOf<String,Any>()
            // локальная функция
            fun scanValue(itInQuotes: Boolean): Any{
                var value:String=""
                if(itInQuotes){
                    while (code[index].toString() != STR_CONTAINS && index < code.length){
                        value += code[index]
                        index ++
                    }
                    index ++
                    return value
                }
                else{
                    while (code[index].toString().toDoubleOrNull()!= null && index < code.length){
                        value += code[index]
                        if(index+1 == code.length){
                            break
                        }
                        index ++
                    }
                    return value
                }
            }
            //работа функции
            var tag: String =""
            while(index +1 < code.length){
                if (code[index].toString() == STR_CONTAINS){
                    index++
                    tagToValue[tag] = scanValue(true)
                    tag = ""
                    continue
                }
                else if (code[index].toString().toDoubleOrNull()!= null){
                    tagToValue[tag] = scanValue(false)
                    tag = ""
                    continue
                }
                tag += code[index]
                index ++
                    continue
            }
            return tagToValue
        }
        //------------------------
        //Декодер
        //-------------------------

    }
}
