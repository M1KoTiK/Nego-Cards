package m1k.kotik.negocards.data.qrc

import m1k.kotik.negocards.data.date.SimpleDate

enum class CodeContentType(val typeName: String, val desc: String? = null){
    Text("Текст", "Обобщенный тип содержимого в котором может содержаться любая текстовая информация"),
    Reference("Ссылка"),
    Location("Геоданные"),
    Canvas("Холст", "Позволяет сохранить визуальную информацию в виде кода"),
}
enum class CodeType(val typeName: String, val desc: String? = null){
    QRC("QR-код"),
    Barcode("Штрих-код", "В этом приложении пока нет возможности создавать такие коды, поэтому вместо него видно QR-код"),
    DataMatrix("DataMatrix-код", "В этом приложении пока нет возможности создавать такие коды, поэтому вместо него видно QR-код")
}
class QRCViewModel(var type: CodeContentType, var value: String, var date: SimpleDate)