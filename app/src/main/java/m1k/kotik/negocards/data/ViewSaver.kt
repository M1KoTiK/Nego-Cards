package m1k.kotik.negocards.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.OnScanCompletedListener
import android.os.Environment
import android.util.Log
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.util.*


class ViewSaver {

    companion object {
        fun getBitmapFromView(view: View): Bitmap {
            val bitmap =
                Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            return bitmap
        }
        const val REQUEST_CODE_EXTERNAL_STORAGE = 112

        fun saveBitmapInGallery(image: Bitmap, context: Context) {
            val root = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            ).toString()
            val myDir = File("$root/NegoCardsImages")
            myDir.mkdirs()
            val generator = Random()

            val uniqueID: String = UUID.randomUUID().toString()
            val fname = "NegoCards-$uniqueID.jpg"
            val file = File(myDir, fname)
            if (file.exists()) file.delete()
            try {
                val out = FileOutputStream(file)
                image.compress(Bitmap.CompressFormat.JPEG, 90, out)
                // sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                //     Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
                out.flush()
                out.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            MediaScannerConnection.scanFile(
                context, arrayOf(file.toString()), null,
                OnScanCompletedListener { path, uri ->
                    Log.i("ExternalStorage", "Scanned $path:")
                    Log.i("ExternalStorage", "-> uri=$uri")
                })




//            if (ContextCompat.checkSelfPermission(context,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED
//            ) {
//                var fileOutputStream: FileOutputStream? = null
//                val path: File = Environment
//                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//                val uniqueID: String = UUID.randomUUID().toString()
//                val file = File(path, "$uniqueID.jpg")
//                try {
//                    fileOutputStream = FileOutputStream(file)
//                } catch (e: FileNotFoundException) {
//                    e.printStackTrace()
//                }
//                image.compress(Bitmap.CompressFormat.JPEG, 30, fileOutputStream)
//                try {
//                    fileOutputStream!!.flush()
//                    fileOutputStream.close()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            } else {
//                println("no permission")
//            }

        }
    }
}