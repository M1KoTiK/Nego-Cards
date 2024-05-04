package m1k.kotik.negocards.data.code.code_action

import android.content.Context
import m1k.kotik.negocards.data.code.Code

interface ICodeAction {
    val nameAction: String
    val action: (Context, Code) -> Unit
}