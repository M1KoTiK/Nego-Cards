package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.texts

import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasDrawable
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasMeasurable
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasTextInputable
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasZoomable
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject

interface ICanvasText : ICanvasDrawable, ICanvasZoomable, ICanvasMeasurable, ICanvasTextInputable,
    ISerializationObject