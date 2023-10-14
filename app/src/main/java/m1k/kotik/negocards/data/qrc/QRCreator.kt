package m1k.kotik.negocards.data.qrc

import android.graphics.Bitmap
import android.graphics.Color
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder

class QRCreator {
    companion object{
    fun getQRCToBitmap(data: String):Bitmap {
        val qrgEncoder = QRGEncoder(data, null, QRGContents.Type.TEXT, 2000)
        qrgEncoder.colorBlack = Color.BLACK
        qrgEncoder.colorWhite = Color.WHITE
        return qrgEncoder.getBitmap(2)
    }
    }
}