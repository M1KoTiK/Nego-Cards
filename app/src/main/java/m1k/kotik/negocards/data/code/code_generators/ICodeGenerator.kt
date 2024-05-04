package m1k.kotik.negocards.data.code.code_generators

import android.graphics.Bitmap

interface ICodeGenerator {
    fun generateCodeBitmap(valueForCoding: String):Bitmap
}