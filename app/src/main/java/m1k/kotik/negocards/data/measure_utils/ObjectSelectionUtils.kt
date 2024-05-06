package m1k.kotik.negocards.data.measure_utils

import android.graphics.Point

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