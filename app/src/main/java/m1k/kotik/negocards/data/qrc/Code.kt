package m1k.kotik.negocards.data.qrc

import m1k.kotik.negocards.data.date.SimpleDate
import m1k.kotik.negocards.data.qrc.code_action.CodeActions
import m1k.kotik.negocards.data.qrc.code_action.ICodeAction

enum class CodeContentType(val typeName: String, actionList: List<ICodeAction>, val desc: String? = null){
    Text(
        "Текст",
        listOf(
            CodeActions.SearchInBrowser()
        ),
        "Обобщенный тип содержимого в котором может содержаться любая текстовая информация"
    ),

    Reference(
        "Ссылка",
        listOf()
    ),

    Location("Геоданные",listOf()),

    Canvas("Холст",listOf(), "Позволяет сохранить визуальную информацию в виде кода"),

}

enum class CodeType(val typeName: String, val desc: String? = null){
    Unknown("Данный тип кода пока что не поддерживается программой"),
    QRC("QR-код"),
    Barcode("Штрих-код", "Линейный штрихкод"),
    DataMatrix("DataMatrix-код", "Двумерный матричный штрихкод"),
    Aztec("Ацтек-код", "Двумерный матричный штрихкод"),
    PDF417("PDF417-код", "Двумерный матричный штрихкод")
}

abstract class Code(var codeType: CodeType, var contentType: CodeContentType, var value: String)
class ScannedCode(codeType: CodeType, contentType: CodeContentType, value: String, var date: SimpleDate): Code(codeType, contentType, value)
// Потом здесь конечно же будет разграничение и для каждого типа будет свой CodeType
    fun barcodeFormatToCodeType(barcodeFormat:Int): CodeType{
        return when(barcodeFormat){
            1-> CodeType.Barcode //FORMAT_CODE_128
            2-> CodeType.Barcode //FORMAT_CODE_39
            4-> CodeType.Barcode //FORMAT_CODE_93
            8-> CodeType.Barcode //FORMAT_CODABAR
            16-> CodeType.DataMatrix //FORMAT_DATA_MATRIX
            32-> CodeType.Barcode //FORMAT_EAN_13
            64-> CodeType.Barcode //FORMAT_EAN_8
            128-> CodeType.Barcode //FORMAT_ITF
            256-> CodeType.QRC //FORMAT_QR_CODE
            512-> CodeType.Barcode //FORMAT_UPC_A
            1024-> CodeType.Barcode //FORMAT_UPC_E
            2048-> CodeType.PDF417 //FORMAT_PDF417
            4096-> CodeType.Aztec //FORMAT_AZTEC
            else-> CodeType.Unknown
        }
    }
const val FORMAT_UPC_A = 512
const val FORMAT_UPC_E = 1024
const val FORMAT_PDF417 = 2048
const val FORMAT_AZTEC = 4096