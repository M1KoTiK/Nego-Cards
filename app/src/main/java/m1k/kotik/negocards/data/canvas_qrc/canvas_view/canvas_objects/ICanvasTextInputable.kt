package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects

/**
 * Для некоторых объектов может потребоваться введение текста снаружи.
 * Для чего на экране редактирования канваса предназначено специальное всплывающее окно
 * метод SetText при переопределении должен изменять внутреннее значение свойства у объекта
 */
interface ICanvasTextInputable {
    var text: String
}