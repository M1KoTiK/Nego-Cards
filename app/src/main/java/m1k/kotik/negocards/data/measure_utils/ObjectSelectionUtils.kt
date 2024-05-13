package m1k.kotik.negocards.data.measure_utils

import android.graphics.Point
import android.graphics.Rect
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.ICanvasMeasurable
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Rectangle

fun isClickOnObject(
    objX: Int,
    objY: Int,
    objWidth: Int,
    objHeight: Int,
    cursorX: Int,
    cursorY: Int,
    offset: Int = 2
): Boolean {
    if (cursorY < objY + objHeight + offset &&
        cursorY > objY - offset &&
        cursorX < objX + objWidth + offset &&
        cursorX > objX - offset) {
        println("true")
        return true
    }
    println("false")
    return false
}

fun displacementByDelta(
    initX: Int,
    initY: Int,
    deltaX:Int,
    deltaY:Int,
    zoom:Int = 1): Point {
        val outX = (initX + deltaX / zoom)

        val outY = (initY + deltaY / zoom)
    return Point(outX, outY)
}

fun getRectForOccupiedSpace(listObjects: List<ICanvasMeasurable>): Rectangle? {
    if(listObjects.isNotEmpty()) {
        val sortedByX = listObjects.sortedBy { it.x }
        val sortedByY = listObjects.sortedBy { it.y }
        val x = sortedByX.first().x
        val y = sortedByY.first().y
        val width = sortedByX.last().width
        val height = sortedByX.last().height
        return Rectangle().also {
            it.x = x
            it.y = y
            it.width = width
            it.height = height
        }
    }
    return null
}