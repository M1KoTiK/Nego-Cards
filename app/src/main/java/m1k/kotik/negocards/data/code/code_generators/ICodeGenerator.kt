package m1k.kotik.negocards.data.code.code_generators

import android.graphics.Bitmap

interface ICodeGenerator {
    var backgroundColor: Int
    var codeColor: Int
    fun generateCodeBitmap(valueForCoding: String):Bitmap
}