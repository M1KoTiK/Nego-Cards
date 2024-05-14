package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.canvas_tools

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_editors.CanvasEditor
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasMeasurable
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Rectangle
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.CanvasButtonTool
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools.PositionFlag
import m1k.kotik.negocards.data.canvas_qrc.old_govno.parseColorFromString
import m1k.kotik.negocards.data.measure_utils.displacementByDelta
import m1k.kotik.negocards.data.measure_utils.getRectForOccupiedSpace

class CanvasMeasureEditTool(override val canvasEditor: CanvasEditor): CanvasButtonTool<ICanvasMeasurable>() {

    override var icon: Drawable? = AppCompatResources.getDrawable(canvasEditor.context, R.drawable.canvasmeasuretoolicon)
    private var initialObjectForEdit = mutableListOf<ICanvasMeasurable>()
    override val onClickDown: (x: Float, y: Float) -> Unit = {
        _ , _ ->
        initialObjectForEdit.clear()
        initialObjectForEdit = objectsForEdit.map{obj ->
            Rectangle().also { rc ->
                rc.x = obj.x
                rc.y = obj.y
                rc.width = obj.width
                rc.height = obj.height
            }
        }.toMutableList()
    }
    override var positionFlags: MutableList<PositionFlag> = mutableListOf(PositionFlag.Bottom, PositionFlag.Right, PositionFlag.XYAsCenter)
    override var onMovingWhenPressed: (deltaX: Float, deltaY: Float, x: Float, y: Float) -> Unit= {
        deltaX, deltaY, _, _ ->
        for (i in 0 until initialObjectForEdit.count()){
            val obj = initialObjectForEdit[i]
            println("objX = ${obj.x}")
            objectsForEdit[i].width = (obj.width + deltaX.toInt())
                .coerceIn(50..1000)
            objectsForEdit[i].height = (obj.height + deltaY.toInt())
                .coerceIn(50..1000)
        }

    }
}