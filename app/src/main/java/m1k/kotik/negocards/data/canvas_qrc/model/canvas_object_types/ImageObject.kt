package m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types

import android.graphics.*
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject

class ImageObject(
    posX: Int = 0,
    posY: Int = 0,
    width: Int = 0,
    height: Int = 0,
    color: String = "FF181818",
    style: CanvasObjectSerializationTag.Style
) : CanvasObject(CanvasObjectType.Image, posX, posY, width, height,color,style) {
    constructor(): this(
        CanvasObjectSerializationTag.WidthTag.default,
        CanvasObjectSerializationTag.Height.default,
        CanvasObjectSerializationTag.PosX.default,
        CanvasObjectSerializationTag.PosY.default,
        CanvasObjectSerializationTag.Color.default,
        CanvasObjectSerializationTag.Style.Fill())
    var imgBitmap: Bitmap? = null
        private set
    var imgUrl: String = CanvasObjectSerializationTag.ImageUrl.default
        set(value){
            if(value.isNotEmpty()) {
                field = value
                updateBitmap()
            }
        }
    init {
        updateBitmap()
    }
    fun updateBitmap(){
        Picasso.get().load(imgUrl).placeholder(R.drawable.logowithsquare__big_).error(R.drawable.logowithsquare__big_).into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                imgBitmap = bitmap
            }
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

            }
            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
        })
    }


    override fun draw(canvas: Canvas) {
        if(imgBitmap!= null) {
            canvas.drawBitmap(
                imgBitmap!!,
                null,
                RectF(posX.toFloat(),posY.toFloat(),posX.toFloat()+ width, posY.toFloat()+ height),
                null
            )
        }
    }

}