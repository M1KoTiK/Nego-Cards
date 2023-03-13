package m1k.kotik.negocards.data.canvas_qrc.model

enum class ObjectType(val tag: String) {
    Text("tx"),
    Shape("sh"),
    Image("img"),
    Reference("ref")
}


abstract class CanvasObject(
    val typeObj: ObjectType,
    val width: Int,
    val height: Int,
    val posX: Int,
    val posY: Int,
) {
    open abstract fun encode():String
}