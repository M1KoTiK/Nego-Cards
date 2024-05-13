package m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_tools

import android.graphics.Point
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasMeasurable
import m1k.kotik.negocards.data.measure_utils.getRectForOccupiedSpace

enum class PositionFlag{
    Left,
    Right,
    Top,
    Bottom,
    XYAsCenter,
    OnCorner
}

abstract class AutoPositionableCanvasTool<T>: TouchProcessingCanvasTool<T>() {
    open var positionFlags: MutableList<PositionFlag> = mutableListOf(PositionFlag.Bottom, PositionFlag.Right, PositionFlag.XYAsCenter)
    private var _objectForEdit: List<T> = listOf()
    override var objectsForEdit: List<T>
        get() = _objectForEdit
        set(value) {
            if(value.isNotEmpty()) {
                _objectForEdit = value
            }
        }
    final override var onPositioning: (List<T>) -> Point = {
        var desiredX: Int = 0
        var desiredY: Int = 0
        val listCanvasMeasurableObjects: MutableList<ICanvasMeasurable> = mutableListOf()
        objectsForEdit.forEach { obj ->
            if(obj is ICanvasMeasurable){
                val canvasMeasurableObj = obj as ICanvasMeasurable
                listCanvasMeasurableObjects.add(canvasMeasurableObj)
            }
        }
        val occupiedSpaceRect = getRectForOccupiedSpace(listCanvasMeasurableObjects)
        if (occupiedSpaceRect != null) {
            positionFlags.forEach { flag ->
                when (flag) {
                    PositionFlag.Left -> {
                        desiredX = occupiedSpaceRect.x
                        if(positionFlags.contains(PositionFlag.OnCorner)){
                            desiredX -= width
                        }
                    }

                    PositionFlag.Right -> {
                        desiredX = occupiedSpaceRect.x + occupiedSpaceRect.width
                    }

                    PositionFlag.Bottom -> {
                        desiredY = occupiedSpaceRect.y + occupiedSpaceRect.height
                    }

                    PositionFlag.Top -> {
                        desiredY = occupiedSpaceRect.y
                        if(positionFlags.contains(PositionFlag.OnCorner)){
                            desiredY -= height
                        }
                    }
                    else -> {}
                }
            }
            if (positionFlags.contains(PositionFlag.XYAsCenter)) {
                desiredX -= width / 2
                desiredY -= height / 2
            }

        }
        Point(desiredX, desiredY)
    }

}