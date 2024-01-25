package m1k.kotik.negocards.data.qrc.code_action

import android.content.Context
import m1k.kotik.negocards.data.qrc.Code

interface ICodeAction {
    val nameAction: String
    val action: (Context, Code) -> Unit
}