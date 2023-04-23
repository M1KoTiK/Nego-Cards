package m1k.kotik.negocards.data.canvas_qrc.model

import android.graphics.Color

fun getHexString(color:Int): String {
    return String.format("#%08X", -0x1 and color)
}
fun  parseColorFromString(color: String): Int {
    if(color.length !=8){
        if(color.elementAt(0) == '#'){
            return Color.parseColor(color)
        }
        return Color.parseColor("#FFFFFFFF")
    }
    return Color.parseColor("#$color")
}
class ColorUtils {

}