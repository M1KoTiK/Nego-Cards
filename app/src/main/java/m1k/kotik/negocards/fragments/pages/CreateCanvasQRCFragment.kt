package m1k.kotik.negocards.fragments.pages

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.model.*
import m1k.kotik.negocards.data.canvas_qrc.model.popup_windows.ColorPickerPopupWindow
import m1k.kotik.negocards.databinding.FragmentCreateCanvasQRCBinding
import m1k.kotik.negocards.fragments.utils_fragment.IOnBackPressedListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class CreateCanvasQRCFragment : Fragment(), IOnBackPressedListener {
    private var selectedItemPosition : Int = -1
    private var binding: FragmentCreateCanvasQRCBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateCanvasQRCBinding.inflate(inflater,container,false)
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle: Bundle = this.requireArguments()
        binding?.textView3?.text = ""
        binding?.view?.changeViewSize(bundle.getInt("width"),bundle.getInt("height"))
        binding?.view?.requestLayout()
        binding?.view?.onCurrentSelectedObjectChange = {
            if(binding?.view?.currentSelectedObject!=null){
                //**Когда выбран хотя бы один объект на канвасе
                val selectedObject = binding?.view?.currentSelectedObject
                binding?.buttonminus?.setImageResource(R.drawable.minus)
                binding?.textView3?.text = selectedObject!!.encode()
                //binding?.button5?.
                binding?.button5?.setImageResource(R.drawable.painticon)
            }
            else{
                //**Когда не выбран не один объект на канвасе
                binding?.button5?.setImageResource(R.drawable.paintgrayicon)
                binding?.buttonminus?.setImageResource(R.drawable.grayminus)
                binding?.textView3?.text = ""
            }
        }
        binding?.buttonminus?.setOnClickListener {
            //нажатие кнопки удаления
            if(binding?.view?.currentSelectedObject!=null) {
                binding?.view?.currentSelectedObject!!.isSelectMode = false
                binding?.view?.currentSelectedObject!!.isEditMode = false
                binding?.view?.deleteSelectedObject()
                binding?.view?.invalidate()
            }
        }
        binding?.buttonplus?.setOnClickListener {
            //нажатие кнопки добавления
            selectedItemPosition = -1
            val addCanvasObjectPopupWindow =
                m1k.kotik.negocards.data.canvas_qrc.model.popup_windows.AddCanvasObjectPopupWindow(
                    {
                        binding?.view?.addCanvasObjects(it.classType)
                        binding?.view?.invalidate()
                    },
                    {

                    })
            addCanvasObjectPopupWindow.setup(
                requireActivity(),
                700, 700)
            addCanvasObjectPopupWindow.show(20, 300, Gravity.TOP or Gravity.LEFT)
        }
        binding?.button5?.setOnClickListener {
            //Нажатие кнопки выбора цвета
            val selectedObject = binding?.view?.currentSelectedObject
            if(selectedObject!=null) {
                val clp = ColorPickerPopupWindow {
                    selectedObject.color = getHexString(it)
                    binding?.view?.invalidate()
                }
                clp.setup(requireActivity(), 900, 700)
                clp.show(50, 1250, Gravity.TOP or Gravity.LEFT)
            }
        }
        fun saveImage(imgBitmap : Bitmap) : Uri {

            // create filename from timestamp
            val timeStamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val fileName : String = "image_${timeStamp}.jpg"

            // get app image directory
            val storagePath : String = Environment.getExternalStorageDirectory().toString() + "/" + Environment.DIRECTORY_DCIM + "/" + "NegoCards";
            val storageDir = File("$storagePath")
            if(!storageDir.exists()) {
                Log.i("TAG", "Directory is newly created")
                storageDir.mkdirs()
            }
            if (storageDir.exists()) {
                Log.i("TAG", "Directory is available")
            }

            // Save image to storage
            val imageFile = File(storageDir,fileName)
            Log.i("TAG", "File created with path ${imageFile.absolutePath}")
            try {
                val fOut = FileOutputStream(imageFile)
                imgBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut)
                fOut.flush()
                fOut.close()
                Log.i("TAG", "Image file created.")
            } catch (e: IOException) {
                e.printStackTrace()
                Log.i("TAG", "Image file not success.")
            }

            // Parse the gallery image url to uri
            return Uri.parse(imageFile.absolutePath)
        }

        binding?.buttonPalochki?.setOnClickListener {
            // Save the image in external storage and get the uri
            Toast.makeText(context, "1234", Toast.LENGTH_SHORT).show()
            saveImage(binding?.view?.myBitmap!!)
            // Display the external storage saved image in image view

        }


        binding?.textView3?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                //cObj.clear()
                try {
                   // testObj.applyCode(binding?.textView3?.text.toString())
                    //cObj.add(testObj)
                }
                catch(e:Exception){
                    Toast.makeText(requireActivity(),e.message,Toast.LENGTH_SHORT).show()
                }
               // binding?.view?.setCanvasObjects(cObj)
                binding?.view?.invalidate()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onBackPressed() {
        Toast.makeText(requireActivity(),"backpressed", Toast.LENGTH_SHORT).show()
    }
}