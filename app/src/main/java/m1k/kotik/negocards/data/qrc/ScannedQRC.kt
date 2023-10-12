package m1k.kotik.negocards.data.qrc

import java.util.Date

enum class QRCType(val typeName: String){
    Text("Текст"),
    Reference("Ссылка"),
    Location("Геоданные"),
    Canvas("Холст"),
}

class ScannedQRC(var type: QRCType, var value: String, var date: Date)