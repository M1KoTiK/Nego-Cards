package m1k.kotik.negocards.data.canvas_qrc.canvas_objects_info

class CanvasObjectInfo(val name: String,val desc: String) {}

val dictionaryObjectInfoBySerializationKey: Map<String,CanvasObjectInfo> = mapOf(
    "rc" to CanvasObjectInfo("Квадрат", "Простой четырехугольник")
)

