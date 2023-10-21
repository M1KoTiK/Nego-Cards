package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects

import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.serializationObject.SeriаlizationMember

abstract class CanvasObject(
    @SeriаlizationMember("y")
    var y: Int = 0,
    @SeriаlizationMember("x")
    var x: Int = 0,
    @SeriаlizationMember("w")
    var width: Int = 0,
    @SeriаlizationMember("h")
    var height: Int = 0
) : ISerializationObject {

}