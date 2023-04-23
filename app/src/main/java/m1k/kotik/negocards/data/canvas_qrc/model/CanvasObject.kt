package m1k.kotik.negocards.data.canvas_qrc.model

import android.graphics.*
import android.graphics.Color.parseColor
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.ArcShape
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.OvalShape
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectRShape
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectShape

abstract class CanvasObject(
    var type: CanvasObjectType,
    var width: Int,
    var height: Int,
    var posX: Int,
    var posY: Int,
    var color: String,
    var style: CanvasObjectSerializationTag.Style,
) {
    var isSelectMode: Boolean = false
        set(value) {
            if (value == true){
                isEditMode = false
            }
            field = value
        }

    var isEditMode: Boolean = false
        set(value) {
            if (value == true){
                isSelectMode = false
            }
            field = value
        }

    open val centerX: Int get() = this.posX + this.width/2
    open val centerY: Int get() = this.posY + this.height/2

    abstract fun draw(canvas: Canvas)

    open fun isCursorHoveredOver(x: Int, y: Int):Boolean{
        if(x>posX && x < posX+width&& y > posY && y < posY+height){
            return true
        }
        return false
    }
    open fun move(x: Int,y: Int){
        this.posX = x - this.width / 2
        this.posY = y - this.height / 2
    }

    open fun encode():String{
        var code: String = ""
        for (objTag in this.type.listTag) {
            val field = objTag.getField(this)
            if (field.toString() == objTag.default.toString()){

            }
            else {
                if (field.toString().toDoubleOrNull() != null){
                    code += "${objTag.name}${field}"
                }
                else{
                    code += "${objTag.name}${STR_CONTAINS}${field}${STR_CONTAINS}"
                }
            }
        }
        return code
    }

    open fun applyCode(code: String){
        val tagToValue = SplitValuesAndTags(code)
        for (tag in this.type.listTag) {
            if(tagToValue.containsKey(tag.name)){
                tag.setField(this, tagToValue[tag.name]!!)
            }
        }
    }

//------------------------------------
// классы типов объектов
//------------------------------------
    sealed class CanvasObjectType {
        abstract val tag: String
        abstract val classType: CanvasObject
        abstract val visibleName: String
        open val isSubsection: Boolean = false
        open val subsectionList = mutableListOf<String>()
        open val listTag =listOf(
        CanvasObjectSerializationTag.ObjectTypeTag,
        CanvasObjectSerializationTag.WidthTag,
        CanvasObjectSerializationTag.Height,
        CanvasObjectSerializationTag.PosX,
        CanvasObjectSerializationTag.PosY,
        CanvasObjectSerializationTag.Color,
        CanvasObjectSerializationTag.Style())

        class Text: CanvasObjectType() {
            override val tag: String get() = "tx"
            override val classType: CanvasObject get() = TextObject()
            override val visibleName: String get() = "Текст"
            override val listTag: List<CanvasObjectSerializationTag>
                get() = super.listTag + listOf(CanvasObjectSerializationTag.Text,CanvasObjectSerializationTag.FontSize())
        }
        open class Shape: CanvasObjectType(){
            override val tag: String get() = "sh"
            override val isSubsection: Boolean get() = true
            override val classType: CanvasObject get() = ShapeObject()
            override val visibleName: String get() = "Фигура"
            override var listTag: List<CanvasObjectSerializationTag> =
                super.listTag + listOf(CanvasObjectSerializationTag.ShapeType())

            class RectR: Shape() {
                override  val tag: String get()= "rr"
                override val isSubsection: Boolean get() = false
                override val visibleName: String get() = "Капсула"
                override val classType: CanvasObject get() = RectRShape()
                override var listTag: List<CanvasObjectSerializationTag> =
                    super.listTag + listOf(CanvasObjectSerializationTag.LeftCorner,CanvasObjectSerializationTag.RightCorner)
            }
            class Quadril: Shape() {
                override val isSubsection: Boolean get() = false
                override val visibleName: String get() = "Четырехугольник"
                override val tag: String get() = "qd"
            }
            class Arc: Shape() {
                override val isSubsection: Boolean get() = false
                override val tag: String get() = "ac"
                override val classType: CanvasObject get() = ArcShape()
                override val visibleName: String get() = "Арка"
            }
            class Rect: Shape(){
                override val isSubsection: Boolean get() = false
                override val tag: String get() = "rc"
                override val classType: CanvasObject get() = RectShape()
                override val visibleName: String get() = "Квадрат"
            }
            class Oval: Shape(){
                override val isSubsection: Boolean get() = false
                override val tag: String get() = "ov"
                override val classType: CanvasObject get() = OvalShape()
                override val visibleName: String get() = "Овал"
            }
        }
    }

//------------------------------
// Классы тегов
//------------------------------
    sealed class CanvasObjectSerializationTag{
        abstract val name: String
        abstract val visiblePropertyName: String
        abstract val default: Any
        abstract fun setField(canvasObject: CanvasObject, value:Any)
        abstract fun getField(canvasObject: CanvasObject):Any

    object ObjectTypeTag : CanvasObjectSerializationTag() {
        override val default: CanvasObjectType get() = CanvasObjectType.Text()
        override val name: String get() = "ot"
        override val visiblePropertyName: String get() = "Тип объекта"
        override fun setField(canvasObject: CanvasObject, value: Any) {
            canvasObject.type = value as CanvasObjectType
        }

        override fun getField(canvasObject: CanvasObject): String {
            return canvasObject.type.tag
        }
    }

    object WidthTag : CanvasObjectSerializationTag() {
        override val default: Int get() = 100
        override val name: String get() = "w"
        override val visiblePropertyName: String get() = "Ширина"
        override fun setField(canvasObject: CanvasObject, value: Any) {
            canvasObject.width = value.toString().toInt()
        }

        override fun getField(canvasObject: CanvasObject): String {
            return canvasObject.width.toString()
        }
    }

    object Height : CanvasObjectSerializationTag() {
        override val default: Int get() = 100
        override val name: String get() = "h"
        override val visiblePropertyName: String get() = "Высота"
        override fun setField(canvasObject: CanvasObject, value: Any) {
            canvasObject.height = value.toString().toInt()
        }

        override fun getField(canvasObject: CanvasObject): Any {
            return canvasObject.height.toString()
        }
    }

    object PosX : CanvasObjectSerializationTag() {
        override val default: Int get() = 50
        override val name: String get() = "x"
        override val visiblePropertyName: String get() = "Позиция x"
        override fun setField(canvasObject: CanvasObject, value: Any) {
            canvasObject.posX = value.toString().toInt()
        }

        override fun getField(canvasObject: CanvasObject): String {
            return canvasObject.posX.toString()
        }
    }

    object PosY : CanvasObjectSerializationTag() {
        override val default: Int get() = 50
        override val name: String get() = "y"
        override val visiblePropertyName: String get() = "Позиция y"
        override fun setField(canvasObject: CanvasObject, value: Any) {
            canvasObject.posY = value.toString().toInt()
        }

        override fun getField(canvasObject: CanvasObject): String {
            return canvasObject.posY.toString()
        }
    }

    object Text : CanvasObjectSerializationTag() {
        override val default: String get() = "text"
        override val name: String get() = "t"
        override val visiblePropertyName: String get() = "Текст"
        override fun setField(canvasObject: CanvasObject, value: Any) {
            canvasObject as TextObject
            canvasObject.text = value.toString()
        }

        override fun getField(canvasObject: CanvasObject): String {
            canvasObject as TextObject
            return canvasObject.text.toString()
        }
    }

    object Color : CanvasObjectSerializationTag() {
        override val default: String get() = "FF181818"
        override val name: String get() = "cl"
        override val visiblePropertyName: String get() = "Цвет"
        override fun setField(canvasObject: CanvasObject, value: Any) {
            canvasObject.color = value.toString()
        }

        override fun getField(canvasObject: CanvasObject): String {
            return canvasObject.color.toString()
        }
    }

        class FontSize: CanvasObjectSerializationTag(){
            override val default: Int get() = 100
            override val name: String get() = "fs"
            override val visiblePropertyName: String get() = "Размер шрифта"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject as TextObject
                canvasObject.fontSize = value.toString().toInt()
            }
            override fun getField(canvasObject: CanvasObject): String {
                canvasObject as TextObject
                return canvasObject.fontSize.toString()
            }
        }

        open class ShapeType: CanvasObjectSerializationTag(){
            override val default: CanvasObjectType.Shape get() = CanvasObjectType.Shape.RectR()
            override val name: String get() = "st"
            override val visiblePropertyName: String get() = "Тип фигуры"
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject as ShapeObject
                canvasObject.shapeType = value as CanvasObjectType.Shape
            }
            override fun getField(canvasObject: CanvasObject): String {
                canvasObject as ShapeObject
                return canvasObject.shapeType.tag.toString()
            }
        }

    object StartAngle : CanvasObjectSerializationTag() {
        override val default: Int get() = 45
        override val name: String get() = "sg"
        override val visiblePropertyName: String get() = "Начальный угол"
        override fun setField(canvasObject: CanvasObject, value: Any) {
            canvasObject as ArcShape
            canvasObject.startAngle = value.toString().toInt()
        }

        override fun getField(canvasObject: CanvasObject): String {
            canvasObject as ArcShape
            return canvasObject.startAngle.toString()
        }

    }

    object SweepAngle : CanvasObjectSerializationTag() {
        override val default: Int get() = 270
        override val name: String get() = "sa"
        override val visiblePropertyName: String get() = "Конечный угол"
        override fun setField(canvasObject: CanvasObject, value: Any) {
            canvasObject as ArcShape
            canvasObject.sweepAngle = value.toString().toInt()
        }

        override fun getField(canvasObject: CanvasObject): String {
            canvasObject as ArcShape
            return canvasObject.sweepAngle.toString()
        }
    }
    object UseCenter : CanvasObjectSerializationTag() {
        override val default: Boolean get() = true
        override val name: String get() = "uc"
        override val visiblePropertyName: String get() = "Использовать центр"
        override fun setField(canvasObject: CanvasObject, value: Any) {
            canvasObject as ArcShape
            canvasObject.useCenter = value as Boolean
        }

        override fun getField(canvasObject: CanvasObject): String {
            canvasObject as ArcShape
            return canvasObject.useCenter.toString()
        }
    }

    object LeftCorner : CanvasObjectSerializationTag() {
        override val default: Int get() = 25
        override val name: String get() = "lc"
        override val visiblePropertyName: String get() = "Закругление слева"
        override fun setField(canvasObject: CanvasObject, value: Any) {
            canvasObject as RectRShape
            canvasObject.leftCorner = value.toString().toInt()
        }

        override fun getField(canvasObject: CanvasObject): Any {
            canvasObject as RectRShape
            return canvasObject.leftCorner.toString()
        }

    }

    object RightCorner : CanvasObjectSerializationTag() {
        override val default: Int get() = 25
        override val name: String get() = "rc"
        override val visiblePropertyName: String get() = "Закругление справа"
        override fun setField(canvasObject: CanvasObject, value: Any) {
            canvasObject as RectRShape
            canvasObject.rightCorner = value.toString().toInt()
        }

        override fun getField(canvasObject: CanvasObject): Any {
            canvasObject as RectRShape
            return canvasObject.rightCorner.toString()
        }

    }

        open class Style :CanvasObjectSerializationTag() {
            open val sType: Paint.Style = Paint.Style.FILL
            override val name: String get() = "s"
            override val visiblePropertyName: String get() = "Стиль"
            override val default: Style get() = Fill()
            override fun setField(canvasObject: CanvasObject, value: Any) {
                canvasObject.style = value as Style
            }
            override fun getField(canvasObject: CanvasObject): Any {
                return canvasObject.style.name
            }
            class Fill : Style() {
                override val name: String get() = "sf"
                override val visiblePropertyName: String get() = "Заполнение"
                override val sType: Paint.Style = Paint.Style.FILL

            }
            class Stroke : Style() {
                override val name: String get() = "ss"
                override val visiblePropertyName: String get() = "Обводка"
                override val sType: Paint.Style = Paint.Style.STROKE

            }
        }

    object StrokeWidth: CanvasObjectSerializationTag() {
        override val default: Int get() = 7
        override val name: String get() = "sw"
        override val visiblePropertyName: String get() = "Ширина обводки"
        override fun setField(canvasObject: CanvasObject, value: Any) {
            canvasObject as ShapeObject
            canvasObject.strokeWidth = value.toString().toInt()
        }

        override fun getField(canvasObject: CanvasObject): Any {
            canvasObject as ShapeObject
            return canvasObject.strokeWidth.toString()
        }
        }
    }

//Дополнительный функционал
    protected fun getParseColor(): Int {
        return parseColorFromString(color)
    }


    companion object{
        const val STR_CONTAINS = '\"'
        const val SEPARATOR_SYMBOL = ';'

        var searchableListCanvasObjectTypes = listOf<CanvasObjectType>(
            CanvasObjectType.Text(),
            CanvasObjectType.Shape(),
            CanvasObjectType.Shape.RectR(),
            CanvasObjectType.Shape.Arc(),
            CanvasObjectType.Shape.Rect(),
            CanvasObjectType.Shape.Oval()
        )
        fun SplitValuesAndTags(code: String):MutableMap<String,Any>{
            var code = code.filter { !it.isWhitespace() }
            var index: Int = 0
            var tagToValue = mutableMapOf<String,Any>()
            var tag: String =""
            // локальные функции

            fun scanQuotes():Boolean{
                var summ: Int = 0
                for(char in code){
                    if(char == STR_CONTAINS) {
                        summ++
                    }
                }
                if(summ%2==0){
                    return true
                }
                return false
            }

            fun scanValue(itInQuotes: Boolean): Any{
                var value:String=""
                if(itInQuotes){
                    while (code[index] != STR_CONTAINS && index < code.length){
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
                }
                return value
            }
            //работа функции
            if(!scanQuotes()){
                return tagToValue
            }
            try {
                while(index +1 < code.length){
                    if (code[index] == STR_CONTAINS){
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
            }
            catch (e: Exception){

            }
            return tagToValue

        }
        //------------------------
        //Декодер
        //-------------------------

    }
}
