package m1k.kotik.negocards.data.canvas_qrc.model

import kotlin.math.pow
import kotlin.math.sqrt


fun getDistanceBetweenPoints(Ax: Float,Ay: Float,Bx: Float, By: Float): Double {
    return sqrt((Bx - Ax).toDouble().pow(2.0) + (By - Ay).toDouble().pow(2.0))
}
class MathUtils {

}