package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.canvas_tools

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_editors.CanvasEditor
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Rectangle
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.CanvasButtonTool
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.PositionFlag

class DeleteObjectTool<T>(override val canvasEditor: CanvasEditor): CanvasButtonTool<T>() {
    override var icon: Drawable? = AppCompatResources.getDrawable(canvasEditor.context, R.drawable.canvas_delete_tool_icon)
    var isPressed = false
    override val onClickDown: (x: Float, y: Float) -> Unit = {
            _ , _ ->
        if (objectsForEdit.isNotEmpty()){
            objectsForEdit.forEach { t ->
                canvasEditor.deleteObject(t as Any)
            }
            objectsForEdit.clear()
            canvasEditor.listCurrentSelectedObjects.clear()
            canvasEditor.invalidate()
        }
    }
    override var positionFlags: MutableList<PositionFlag> = mutableListOf(PositionFlag.Top, PositionFlag.Left, PositionFlag.XYAsCenter)
}