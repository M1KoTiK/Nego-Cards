package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.canvas_tools

import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_editors.CanvasEditor
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.CanvasButtonTool
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.PositionFlag

class EditObjectTool<T>(override val canvasEditor: CanvasEditor): CanvasButtonTool<T>()  {
    override var positionFlags: MutableList<PositionFlag> = mutableListOf(PositionFlag.Top, PositionFlag.Right, PositionFlag.XYAsCenter)
    override var icon: Drawable? = null

}