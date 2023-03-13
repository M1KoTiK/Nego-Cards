package m1k.kotik.negocards.data.canvas_qrc.model

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
    val posX: Int,
    val posY: Int,
) {
    open abstract fun encode():String
    open abstract fun decode(encodeString: String):CanvasObject

    sealed class ObjectType {
        abstract val tag: String
        open var listTag = listOf<Tag>(
            Tag.ObjType(),
            Tag.Width(),
            Tag.Height(),
            Tag.PosX(),
            Tag.PosY())

        class Text : ObjectType() {
            override val tag: String get() = "text"
            override var listTag: List<Tag> =
                super.listTag + listOf(Tag.Color(),Tag.Text(),Tag.FontSize())
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
            override val tag: String get() = TODO("Not yet implemented")
            override fun setField(canvasObject: CanvasObject, value: Any) {
                TODO("Not yet implemented")
            }

            override fun getField(canvasObject: CanvasObject): String {
                TODO("Not yet implemented")
            }
        }

        class PosY: Tag(){
            override val tag: String get() = TODO("Not yet implemented")
            override fun setField(canvasObject: CanvasObject, value: Any) {
                TODO("Not yet implemented")
            }

            override fun getField(canvasObject: CanvasObject): String {
                TODO("Not yet implemented")
            }
        }
        class Text: Tag(){
            override val tag: String get() = TODO("Not yet implemented")
            override fun setField(canvasObject: CanvasObject, value: Any) {
                TODO("Not yet implemented")
            }

            override fun getField(canvasObject: CanvasObject): String {
                TODO("Not yet implemented")
            }
        }
        class Color: Tag(){
            override val tag: String
            get() = TODO("Not yet implemented")

            override fun setField(canvasObject: CanvasObject, value: Any) {
                TODO("Not yet implemented")
            }

            override fun getField(canvasObject: CanvasObject): String {
                TODO("Not yet implemented")
            }
        }
        class FontSize: Tag(){
            override val tag: String
                get() = TODO("Not yet implemented")

            override fun setField(canvasObject: CanvasObject, value: Any) {
                TODO("Not yet implemented")
            }

            override fun getField(canvasObject: CanvasObject): String {
                TODO("Not yet implemented")
            }

        }
    }

}