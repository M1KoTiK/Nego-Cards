package m1k.kotik.negocards.data.canvas_qrc.old_govno

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.view.View
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class CanvasSaver {

    companion object {
        fun getBitmapFromView(view: View): Bitmap {
            val bitmap =
                Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            return bitmap
        }
        const val REQUEST_CODE_EXTERNAL_STORAGE = 112

        fun saveBitmapInGallery(image: Bitmap, context: Context) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                var fileOutputStream: FileOutputStream? = null
                val path: File = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val uniqueID: String = UUID.randomUUID().toString()
                val file = File(path, "$uniqueID.jpg")
                try {
                    fileOutputStream = FileOutputStream(file)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
                image.compress(Bitmap.CompressFormat.JPEG, 30, fileOutputStream)
                try {
                    fileOutputStream!!.flush()
                    fileOutputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                println("no permission")
            }

        }
    }
}