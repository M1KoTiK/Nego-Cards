package m1k.kotik.negocards.data.code.code_action

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.view.Gravity
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import m1k.kotik.negocards.R
import m1k.kotik.negocards.custom_views.windows.stylized_window.FloatingStylizedWindow
import m1k.kotik.negocards.data.canvas_qrc.canvas_serialization.CanvasSerialization
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.BitmapShape
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_editors.CanvasView
import m1k.kotik.negocards.data.code.Code
import m1k.kotik.negocards.data.recycler_view_adapters.text_object_in_canvas.TextObjectInCanvasAdapter
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject


sealed class CodeAction: ICodeAction {
    /**
     * Просто ищет в браузере содержимое кода
     */
    object SearchInBrowser: CodeAction() {
        override val nameAction: String = "Найти в браузере"
        override val action: (Context, Code) -> Unit = { context, code ->
            val browserIntent = if (
                code.value.startsWith("http://") ||
                code.value.startsWith("https://")
            ) { Intent(Intent.ACTION_VIEW, Uri.parse(code.value)) }
            else { Intent(Intent.ACTION_VIEW, Uri.parse("https://ya.ru/search/?text=" + code.value)) }
            context.startActivity(browserIntent)
        }
    }
    /**
     * Открывает ссылку в браузере
     */
    object OpenInBrowser: CodeAction() {
        override val nameAction: String = "Открыть ссылку в браузере"
        override val action: (Context, Code) -> Unit = { context, code ->
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
    object OpenCanvasInWindow: CodeAction(){
        override val nameAction: String = "Открыть холст"
        override val action: (Context, Code) -> Unit = { context, code ->
            val height = context.resources.displayMetrics.heightPixels
            val width = context.resources.displayMetrics.widthPixels
            val canvasViewWindow: FloatingStylizedWindow = FloatingStylizedWindow(context, R.layout.fragment_canvas_viewer).also {
                it.header = "Просмотр холста"
            }
            canvasViewWindow.windowContentViewGroup.also {
                it.background =
                    ResourcesCompat.getDrawable(context.resources, R.drawable.rounded_square_15, null)
                it.backgroundTintList = ColorStateList.valueOf(0xFF252525.toInt())
            }
            canvasViewWindow.rootView.findViewById<LinearLayout>(R.id.window_content).gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            val canvasObjects = CanvasSerialization.deserializeCanvas<ISerializationObject>(code.value)
            if(canvasObjects!=null) {
               canvasViewWindow.contentView.findViewById<CanvasView>(R.id.canvasViewInCanvasViewer).also {
                   if(canvasObjects.first() !is BitmapShape){
                       //TODO исключение: бэкграунд должен быть фигурой
                   }
                   it.setBackgroundObject(canvasObjects.first() as BitmapShape)
                   it.setObjects(canvasObjects.drop(1))
                   it.invalidate()
                   val rec = canvasViewWindow.contentView.findViewById<RecyclerView>(R.id.recycler_list_text_in_object)
                   rec.adapter = TextObjectInCanvasAdapter(context, canvasObjects)
                   rec.layoutManager = LinearLayoutManager(context)


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