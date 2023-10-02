package m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextUtils.lastIndexOf
import android.view.View
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.shapes.ArcShape
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.shapes.OvalShape
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.shapes.RectRShape
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.shapes.RectShape


class QRCDecoder() {
    fun decode(code: String, backgroundObject: CanvasObject):MutableList<CanvasObject> {
        var inputString = code
        var objects = mutableListOf<CanvasObject>()
        objects.add(backgroundObject)
        val objectsInCode = inputString.split(SEPARATOR_CHAR)
        for (objCode in objectsInCode) {
            val objDict = splitValuesAndTags(objCode)
            if (objDict.containsKey("ot")) {
                val tagObjType = objDict["ot"]
                for (objType in listCanvasObjectTypes) {
                    if (objType.tag == tagObjType) {
                        val obj = objType.classType
                        obj.applyCode(objCode)
                        objects.add(obj)
                    }
                }
            }
        }
        return objects
    }
    fun encode(objects: List<CanvasObject>): String {
        var outputString: String = String()
        for (obj in objects) {
            outputString += obj.encode()
            outputString += SEPARATOR_CHAR
        }
        return outputString
    }
}


//------------------------------------
// классы типов объектов
//------------------------------------
sealed class QRCTypes{

}
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
        CanvasObjectSerializationTag.Color)

    object Text: CanvasObjectType() {
        override val tag: String get() = "tx"
        override val classType: CanvasObject get() = TextObject()
        override val visibleName: String get() = "Текст"
        override val listTag: List<CanvasObjectSerializationTag>
            get() = super.listTag + listOf(CanvasObjectSerializationTag.Text,
                CanvasObjectSerializationTag.FontSize)
    }
    object Image: CanvasObjectType() {
        override val tag: String get() = "tx"
        override val classType: CanvasObject get() = ImageObject()
        override val visibleName: String get() = "Картинка"
        override val listTag: List<CanvasObjectSerializationTag>
            get() = super.listTag + listOf(CanvasObjectSerializationTag.ImageUrl)
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
                super.listTag + listOf(CanvasObjectSerializationTag.LeftCorner,
                    CanvasObjectSerializationTag.RightCorner)
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
    open var childTags: List<CanvasObjectSerializationTag> = listOf()
    abstract val default: Any
    open val inpTextAbility: Boolean = false
    fun findFromChildTags(tagName:String):CanvasObjectSerializationTag?{
        if(childTags.isNotEmpty()){
            for(tag in childTags){
                if (tagName == tag.name){
                    return tag
                }
            }
        }
        return null
    }
    abstract fun setField(canvasObject: CanvasObject, value:Any)
    abstract fun getField(canvasObject: CanvasObject):Any

    object ObjectTypeTag : CanvasObjectSerializationTag() {
        override val default: CanvasObjectType get() = CanvasObjectType.Text
        override val name: String get() = "ot"
        override val visiblePropertyName: String get() = "Тип объекта"

        override var childTags: List<CanvasObjectSerializationTag> = super.childTags

        override fun setField(canvasObject: CanvasObject, value: Any) {
            canvasObject.type = value as CanvasObjectType
        }

        override fun getField(canvasObject: CanvasObject): String {
            return canvasObject.type.tag
        }
    }

    object WidthTag : CanvasObjectSerializationTag() {
        override val default: Int get() = 150
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
        override val default: Int get() = 150
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
        override val inpTextAbility = true
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

    object FontSize : CanvasObjectSerializationTag() {
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

    object ImageUrl : CanvasObjectSerializationTag() {
        override val inpTextAbility = true
        override val default: String get() = "https://avatars.mds.yandex.net/i?id=c61176acd6c9b746c66baf28825538920e013c15-7716430-images-thumbs&n=13"
        override val name: String get() = "iu"
        override val visiblePropertyName: String get() = "Ссылка на изображение"
        override fun setField(canvasObject: CanvasObject, value: Any) {
            canvasObject as ImageObject
            canvasObject.imgUrl = value.toString()
        }

        override fun getField(canvasObject: CanvasObject): Any {
            canvasObject as ImageObject
            return canvasObject.imgUrl
        }

    }

    open class Style : CanvasObjectSerializationTag() {
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
    const val STR_CONTAINS_CHAR = '\"'
    const val SEPARATOR_CHAR = ';'

    var listCanvasObjectTypes = listOf(
        CanvasObjectType.Text,
        CanvasObjectType.Image,
        CanvasObjectType.Shape(),
        CanvasObjectType.Shape.RectR(),
        CanvasObjectType.Shape.Arc(),
        CanvasObjectType.Shape.Rect(),
        CanvasObjectType.Shape.Oval(),
    )
fun splitValuesAndTags(code: String):MutableMap<String,Any>{
    var code = code.filter { !it.isWhitespace() }
    var index: Int = 0
    var tagToValue = mutableMapOf<String,Any>()
    var tag: String =""
    // локальные функции

    fun scanQuotes():Boolean{
        var summ: Int = 0
        for(char in code){
            if(char == STR_CONTAINS_CHAR) {
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
            while (code[index] != STR_CONTAINS_CHAR && index < code.length){
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
            if (code[index] == STR_CONTAINS_CHAR){
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
//------------------------------------
// Функции
//------------------------------------
fun isInpTextTagInObject(canvasObject: CanvasObject): MutableList<CanvasObjectSerializationTag>{
    val listNecessaryTags = mutableListOf<CanvasObjectSerializationTag>()
    for(tag in canvasObject.type.listTag ){
        if(tag.inpTextAbility){
            listNecessaryTags.add(tag)
        }
    }
    return listNecessaryTags
}

