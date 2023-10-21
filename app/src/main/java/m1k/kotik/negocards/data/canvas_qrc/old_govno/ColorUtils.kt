package m1k.kotik.negocards.data.canvas_qrc.old_govno

import android.graphics.Color

fun getHexString(color:Int,addHash:Boolean = false): String {
    if (addHash) {
        return "#" + Integer.toHexString(color)
    }
    return Integer.toHexString(color)
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