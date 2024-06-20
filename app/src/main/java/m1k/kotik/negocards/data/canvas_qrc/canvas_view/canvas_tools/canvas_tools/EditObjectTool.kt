package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.canvas_tools

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import m1k.kotik.negocards.R
import m1k.kotik.negocards.custom_views.windows.stylized_window.FloatingStylizedWindow
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_editors.CanvasEditor
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.texts.CanvasText
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.CanvasButtonTool
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.PositionFlag
import m1k.kotik.negocards.data.canvas_qrc.old_govno.parseColorFromString
import m1k.kotik.negocards.data.code.code_generators.QRCGenerator
import kotlin.math.min

class EditObjectTool(override val canvasEditor: CanvasEditor): CanvasButtonTool<CanvasText>()  {
    override var positionFlags: MutableList<PositionFlag> = mutableListOf(PositionFlag.Top, PositionFlag.Right, PositionFlag.XYAsCenter)
    override var icon: Drawable? = AppCompatResources.getDrawable(canvasEditor.context, R.drawable.pen_icon)
    override var backgroundColor: Int = parseColorFromString("FFFF7777")
    override val onClickDown: (x: Float, y: Float) -> Unit = {
            _ , _ ->
        if (objectsForEdit.isNotEmpty() && objectsForEdit.count() == 1 && canvasEditor.currentSelectedObject is CanvasText){
            val selObj =  canvasEditor.currentSelectedObject as CanvasText
            val window = FloatingStylizedWindow(canvasEditor.context, R.layout.text_input_window)
            window.contentView.findViewById<EditText>(R.id.TextInputPT).also{
                it.setText((canvasEditor.listCurrentSelectedObjects.first() as CanvasText).text)
                it.addTextChangedListener(object :
                    TextWatcher {
                    override fun afterTextChanged(s: Editable) {
                    }
                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                        if(s.toString() != "") {
                            (canvasEditor.listCurrentSelectedObjects.first() as CanvasText).text = s.toString()
                            canvasEditor.invalidate()
                        }
                    }
                })
            }

            val height = canvasEditor.context.resources.displayMetrics.heightPixels
            val width = canvasEditor.context.resources.displayMetrics.widthPixels
            window.rootView.findViewById<LinearLayout>(R.id.window_content).also{
                it.background = ResourcesCompat.getDrawable(canvasEditor.context.resources, R.drawable.rounded_square_15, null)
                it.backgroundTintList = ColorStateList.valueOf(0xAA181818.toInt())
            }
            window.show(0,0, width, height,
                Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL)
        }
            canvasEditor.invalidate()
    }
}