package m1k.kotik.negocards.data.canvas_qrc.canvas_serialization

import m1k.kotik.negocards.data.canvas_qrc.canvas_objects.shapes.Rectangle
import m1k.kotik.negocards.data.serialization.serializer.DefaultSerializer
import kotlin.reflect.KClass
class CanvasSerialization {
    private val mapReqObjects: Map<String, KClass<*>> = mutableMapOf(
        "rc" to Rectangle::class
    )
    val serializer = DefaultSerializer(mapReqObjects)

}