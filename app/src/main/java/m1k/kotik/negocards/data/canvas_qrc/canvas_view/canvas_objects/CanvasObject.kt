package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects

import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.serializationObject.SerializeMember

abstract class CanvasObject(
    /**
     * y координата верхнего левого угла для прямоугольника в который впиысывается фигура
     */
    @SerializeMember("y")
    var y: Int,
    /**
     *  x - координата верхнего левого угла для прямоугольника в который впиысывается фигура
     */
    @SerializeMember("x")
    var x: Int,

    @SerializeMember("w")
    var width: Int,
    @SerializeMember("h")
    var height: Int
) : ISerializationObject{

}