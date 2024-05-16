package m1k.kotik.negocards.data.recycler_view_adapters.canvas_object_types

import android.graphics.drawable.Drawable
import m1k.kotik.negocards.data.recycler_view_adapters.TextIconViewModel

class CanvasObjectTypesViewModel(val getCanvasObject: ()-> Any = {}, icon:Drawable?, name: String): TextIconViewModel(icon, name) {

}