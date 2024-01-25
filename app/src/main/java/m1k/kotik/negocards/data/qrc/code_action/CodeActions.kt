package m1k.kotik.negocards.data.qrc.code_action

import android.content.Context
import android.content.Intent
import android.net.Uri
import m1k.kotik.negocards.data.qrc.Code

sealed class CodeActions: ICodeAction {
    /**
     * Просто ищет в браузере содержимое кода
     */
    class SearchInBrowser(): CodeActions() {
        override val nameAction: String = "Найти в браузере"
        override val action: (Context, Code) -> Unit = {context, code ->
            val browserIntent = if (
                code.value.startsWith("http://") ||
                code.value.startsWith("https://")
            ) { Intent(Intent.ACTION_VIEW, Uri.parse(code.value)) }
            else { Intent(Intent.ACTION_VIEW, Uri.parse("https://ya.ru/search/?text=" + code.value)) }
            context.startActivity(browserIntent)
        }
    }
    /**
     * Открывает экран просмотра холста
     */
}