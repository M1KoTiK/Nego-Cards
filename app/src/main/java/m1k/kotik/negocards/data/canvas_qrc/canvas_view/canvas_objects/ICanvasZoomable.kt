package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects

interface ICanvasZoomable {
    /**
     *  Предствляет значение масштабирования объекта:
     *
     *  0 - наименьший размер;
     *
     *  1 - нормальный размер;
     *
     *  2 - увеличение в два раза;
     *
     *  n - увеличение в n раз.
     */
    var zoomValue: Float
}