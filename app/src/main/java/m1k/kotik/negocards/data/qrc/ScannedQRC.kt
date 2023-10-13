package m1k.kotik.negocards.data.qrc

import m1k.kotik.negocards.data.date.SimpleDate

enum class QRCType(val typeName: String){
    Text("Текст"),
    Reference("Ссылка"),
    Location("Геоданные"),
    Canvas("Холст"),
}

class ScannedQRC(var type: QRCType, var value: String, var date: SimpleDate)