package m1k.kotik.negocards.data.code.code_generators

import android.graphics.Bitmap
import android.graphics.Color
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import m1k.kotik.negocards.data.canvas_qrc.old_govno.parseColorFromString

class QRCGenerator: ICodeGenerator {
    override var codeColor: Int = parseColorFromString("#181818")
    override var backgroundColor: Int = parseColorFromString("#ffffff")

    override fun generateCodeBitmap(valueForCoding: String): Bitmap {
        val qrgEncoder = QRGEncoder(valueForCoding, null, QRGContents.Type.TEXT, 2000)
        qrgEncoder.colorBlack = codeColor
        qrgEncoder.colorWhite = backgroundColor
        return qrgEncoder.getBitmap(2)
    }
}