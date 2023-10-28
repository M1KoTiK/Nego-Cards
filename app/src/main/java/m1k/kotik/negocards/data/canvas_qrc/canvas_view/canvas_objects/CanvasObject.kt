package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects

import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.serializationObject.SerializeMember

abstract class CanvasObject(
    @SerializeMember("y")
    var y: Int,
    @SerializeMember("x")
    var x: Int,
    @SerializeMember("w")
    var width: Int,
    @SerializeMember("h")
    var height: Int
) : ISerializationObject{

}