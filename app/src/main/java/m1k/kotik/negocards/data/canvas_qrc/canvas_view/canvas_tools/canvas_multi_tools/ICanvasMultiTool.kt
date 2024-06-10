package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.canvas_multi_tools

import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.ICanvasTool

abstract class CanvasMultiTool<T>: ICanvasTool<T> {
    abstract val listChildTools: MutableList<ICanvasTool<*>>
}