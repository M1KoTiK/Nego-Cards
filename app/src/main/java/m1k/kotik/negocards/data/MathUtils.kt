package m1k.kotik.negocards.data.canvas_qrc.old_govno

import android.graphics.Point
import kotlin.math.pow
import kotlin.math.sqrt


fun getDistanceBetweenPoints(Ax: Float,Ay: Float,Bx: Float, By: Float): Double {
    return sqrt((Bx - Ax).toDouble().pow(2.0) + (By - Ay).toDouble().pow(2.0))
}
fun isAreaContainsPoint(point: Point, AreaPoint1:Point, AreaPoint2:Point): Boolean{
    if(point.x >= AreaPoint1.x && point.x <= AreaPoint2.x &&
            point.y >= AreaPoint1.y && point.y <= AreaPoint2.y){
        return true
    }
    return false
}