package m1k.kotik.negocards.data.canvas_qrc.canvas_serialization

import m1k.kotik.negocards.data.canvas_qrc.canvas_objects.shapes.Rectangle
import m1k.kotik.negocards.data.serialization.DefaultParser
import m1k.kotik.negocards.data.serialization.serializer.ComplementarySerializer
import kotlin.reflect.KClass

class CanvasSerialization {
    private val mapReqObjects: Map<String, KClass<*>> = mutableMapOf(
        "rc" to Rectangle::class,
    )

    val canvasSerializer = ComplementarySerializer(mapReqObjects).also {
        it.parser.converterSet = CanvasConverterSet()
    }
}

