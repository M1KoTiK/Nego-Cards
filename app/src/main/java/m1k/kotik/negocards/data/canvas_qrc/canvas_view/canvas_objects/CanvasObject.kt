package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects

import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.serializationObject.SerializeMember

abstract class CanvasObject(
    @SerializeMember("y")
    var y: Int = 0,
    @SerializeMember("x")
    var x: Int = 0,
    @SerializeMember("w")
    var width: Int = 0,
    @SerializeMember("h")
    var height: Int = 0
) : ISerializationObject{

}