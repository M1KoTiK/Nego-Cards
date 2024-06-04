package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.canvas_multi_tools

import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.ICanvasTool

interface ICanvasMultiTool: ICanvasTool<Any> {
    val listChildTools: List<ICanvasTool<Any>>


}