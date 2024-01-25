package m1k.kotik.negocards.data.qrc.code_action

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import m1k.kotik.negocards.R
import m1k.kotik.negocards.custom_views.windows.stylized_window.FloatingStylizedWindow
import m1k.kotik.negocards.data.canvas_qrc.canvas_serialization.CanvasSerialization
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.CanvasShape
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.new_canvas_view.CanvasView
import m1k.kotik.negocards.data.qrc.Code


sealed class CodeAction: ICodeAction {
    /**
     * Просто ищет в браузере содержимое кода
     */
    object SearchInBrowser: CodeAction() {
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
     * Открывает окно для просмотра холста
     */
    object OpenCanvasViewPage: CodeAction(){
        override val nameAction: String = "Открыть холст"
        override val action: (Context, Code) -> Unit = {context, code ->


            val height = context.resources.displayMetrics.heightPixels
            val width = context.resources.displayMetrics.widthPixels
            val canvasViewWindow: FloatingStylizedWindow = FloatingStylizedWindow(context, R.layout.fragment_canvas_viewer).also {
                it.header = "Выбор цвета"
            }
            val canvasObjects = CanvasSerialization.canvasSerializer.deserialize<CanvasObject>(code.value)
            if(canvasObjects!=null) {
               canvasViewWindow.contentView.findViewById<CanvasView>(R.id.canvasViewInCanvasViewer).also {
                   if(canvasObjects.first() !is CanvasShape){
                       //TODO исключение: бэкграунд должен быть фигурой
                   }
                   it.setBackgroundObject(canvasObjects.first() as CanvasShape)
                   it.setObjects(canvasObjects.drop(1))
                   it.invalidate()
               }
            }

            canvasViewWindow.show(0,0,width, height,Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
        }
    }

    /**
     * Открывает окно для сохранения контакта в телефонную книгу
     */
    object SaveInContacts: CodeAction(){
        override val nameAction: String = "Добавить контакт в телефонную книгу"
        override val action: (Context, Code) -> Unit = { context, code ->

        }

    }

}