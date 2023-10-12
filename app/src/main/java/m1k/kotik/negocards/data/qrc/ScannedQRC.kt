package m1k.kotik.negocards.data.qrc

import java.util.Date

enum class QRCType(name:String){
    Text("Текст"),
    Reference("Ссылка"),
    Location("Геоданные"),
    Canvas("Холст"),
}

class ScannedQRC(var type: QRCType, var value: String, var date: Date)