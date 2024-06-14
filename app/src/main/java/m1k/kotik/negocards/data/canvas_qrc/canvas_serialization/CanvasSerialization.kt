package m1k.kotik.negocards.data.canvas_qrc.canvas_serialization

import m1k.kotik.negocards.data.canvas_qrc.canvas_serialization.canvas_converters.CanvasConverterSet
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_editors.CanvasEditor
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Oval
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Rectangle
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.RoundRectangle
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.texts.CanvasText
import m1k.kotik.negocards.data.serialization.parser.DefaultParser
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.serializer.DefaultSerializer
import kotlin.reflect.KClass

class CanvasSerialization{
    companion object {
        private val mapReqObjects: Map<String, KClass<*>> = mutableMapOf(
            "rc" to Rectangle::class,
            "rr" to RoundRectangle::class,
            "ov" to Oval::class,
            "ctx" to CanvasText::class
        )
        private val canvasSerializer = DefaultSerializer(DefaultParser(CanvasConverterSet(), mapReqObjects))
        fun serializeCanvas(list: MutableList<ISerializationObject>) : String{
            val out = "canvas:" + CanvasSerialization.canvasSerializer.serialize(list)
            return out
        }
        fun <T>deserializeCanvas(code: String) : List<T>?{
           return CanvasSerialization.canvasSerializer.deserialize<T>(code.drop(7))
        }
    }
}
