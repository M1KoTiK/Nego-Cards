package m1k.kotik.negocards.data.canvas_qrc.model

import android.graphics.*
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.CanvasObjectSerializationTag
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.CanvasObjectType
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.STR_CONTAINS_CHAR
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.splitValuesAndTags

enum class CanvasObjectMode{
    Select,
    Edit,
    Input,
    ModeFree,
    None
}

abstract class CanvasObject(
    var type: CanvasObjectType,
    var width: Int,
    var height: Int,
    var posX: Int,
    var posY: Int,
    var color: String,
    var style: CanvasObjectSerializationTag.Style,
) {
    var mode: CanvasObjectMode = CanvasObjectMode.None
    var isSelectMode: Boolean = false
        set(value) {
            if (value){
                isEditMode = false
                isInputMode = false
            }
            field = value
        }

    var isEditMode: Boolean = false
        set(value) {
            if (value){
                isSelectMode = false
                isInputMode = false
            }
            field = value
        }

    var isInputMode: Boolean = false
        set(value) {
            if (value){
                isSelectMode = false
                isEditMode = false
            }
            field = value
        }

    open val centerX: Int get() = this.posX + this.width/2
    open val centerY: Int get() = this.posY + this.height/2

    abstract fun draw(canvas: Canvas)
    open fun reMeasure(){}


    open fun isCursorHoveredOver(x: Int, y: Int):Boolean{
        if(x>posX && x < posX+width&& y > posY && y < posY+height){
            return true
        }
        return false
    }
    open fun move(x: Int,y: Int){
        this.posX = x
        this.posY = y
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
                    code += "${objTag.name}$STR_CONTAINS_CHAR${field}$STR_CONTAINS_CHAR"
                }
            }
        }
        return code
    }

        open fun applyCode(code: String){
        val tagToValue = splitValuesAndTags(code)
        for (tag in this.type.listTag) {
            if(tagToValue.containsKey(tag.name)){
                tag.setField(this, tagToValue[tag.name]!!)
            }
        }
    }

    protected fun getParseColor(): Int {
        return parseColorFromString(color)
    }

}
