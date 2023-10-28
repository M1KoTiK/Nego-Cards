package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects
enum class CanvasObjectMode{
    Select,
    Edit,
    Input,
    ModeFree,
    None
}
// Показывает в каком режиме находится объект
interface ICanvasEditable {
    var mode: CanvasObjectMode

}