package m1k.kotik.negocards.fragments.pages

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.model.alert_dialogs.InputTextDialog
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.QRCDecoder
import m1k.kotik.negocards.data.canvas_qrc.model.canvas_object_types.isInpTextTagInObject
import m1k.kotik.negocards.data.canvas_qrc.model.getHexString
import m1k.kotik.negocards.data.canvas_qrc.model.popup_windows.CanvasViewerPopupWindow
import m1k.kotik.negocards.data.canvas_qrc.model.popup_windows.CardMenuPopupWindow
import m1k.kotik.negocards.data.canvas_qrc.model.popup_windows.ColorPickerPopupWindow
import m1k.kotik.negocards.data.canvas_qrc.model.popup_windows.QRCViewerPopupWindow
import m1k.kotik.negocards.databinding.FragmentCreateCanvasQRCBinding
import m1k.kotik.negocards.fragments.utils_fragment.IOnBackPressedListener


class CreateCanvasQRCFragment : Fragment(), IOnBackPressedListener {
    private var activityResultLauncher: ActivityResultLauncher<Array<String>>
    init{
        this.activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) {result ->
            var allAreGranted = true
            for(b in result.values) {
                allAreGranted = allAreGranted && b
            }

            if(allAreGranted) {
                cardMenuPopupWindow.setup(requireActivity(), R.layout.card_menu_popup_window, 700,1000)
                cardMenuPopupWindow.show(0,275,Gravity.TOP or Gravity.CENTER_HORIZONTAL)
            }
        }
    }
    var qrcViewer = QRCViewerPopupWindow()
    private val cardMenuPopupWindow = CardMenuPopupWindow()
    private var selectedItemPosition : Int = -1
    private var binding: FragmentCreateCanvasQRCBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateCanvasQRCBinding.inflate(inflater,container,false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle: Bundle = this.requireArguments()
        binding?.textView3?.setText("")
        binding?.view?.changeViewSize(bundle.getInt("width"),bundle.getInt("height"))
        binding?.view?.requestLayout()
        binding?.view?.onCurrentSelectedObjectChange = {
            if(binding?.view?.currentSelectedObject!=null){
                //**Когда выбран хотя бы один объект на канвасе
                val selectedObject = binding?.view?.currentSelectedObject
                binding?.buttonminus?.setImageResource(R.drawable.minus)
                binding?.textView3?.setText(selectedObject!!.encode())
                binding?.button5?.imageTintList = ColorStateList.valueOf(0xFF000000.toInt())
                val inpTags = isInpTextTagInObject(binding?.view?.currentSelectedObject!!)
                if(inpTags.isNotEmpty()){
                    binding?.inpTextBtn?.imageTintList = ColorStateList.valueOf(0xFF000000.toInt())
                }
                else{
                    binding?.inpTextBtn?.imageTintList = ColorStateList.valueOf(0xFF909090.toInt())
                }

            }
            else{
                //**Когда не выбран не один объект на канвасе
                binding?.button5?.imageTintList = ColorStateList.valueOf(0xFF909090.toInt())
                binding?.buttonminus?.setImageResource(R.drawable.grayminus)
                binding?.textView3?.setText("")
                binding?.inpTextBtn?.imageTintList = ColorStateList.valueOf(0xFF909090.toInt())
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

        val inputTextDialog = InputTextDialog(requireActivity())

        inputTextDialog.setOnDismissListener {
            val currentSelectedObject = binding?.view?.currentSelectedObject
            if(currentSelectedObject != null) {
                currentSelectedObject.isSelectMode = true
                binding?.view?.invalidate()
            }
        }

        inputTextDialog.onInpTextChange = {
            val currentSelectedObject = binding?.view?.currentSelectedObject
            if (currentSelectedObject != null) {
                val inpTextTag = isInpTextTagInObject(currentSelectedObject)
                if (inpTextTag.isNotEmpty()) {
                    inputTextDialog.initText = inpTextTag[0].getField(currentSelectedObject).toString()
                    inpTextTag[0].setField(currentSelectedObject,
                        inputTextDialog.inpText.text.toString())
                    currentSelectedObject.reMeasure()
                    binding?.view?.invalidate()
                }
            }
        }


        //Нажатие кнопки для ввода текста в объект на канвасе
        binding?.inpTextBtn?.setOnClickListener {
            val currentSelectedObject = binding?.view?.currentSelectedObject
            if  (currentSelectedObject!= null) {
                val inpTextTag = isInpTextTagInObject(currentSelectedObject)
                if (inpTextTag.isNotEmpty()) {
                    inputTextDialog.initText =
                        inpTextTag[0].getField(currentSelectedObject).toString()
                    inputTextDialog.show()
                }
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
                        val decoder =  QRCDecoder()
                    },
                    {

                    })
            addCanvasObjectPopupWindow.setup(
                requireActivity(),
                700, 700)
            addCanvasObjectPopupWindow.show(20, 300, Gravity.TOP or Gravity.START)
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
                clp.show(50, 1250, Gravity.TOP or Gravity.START)
            }
        }
        var canvasViewer = CanvasViewerPopupWindow()
        cardMenuPopupWindow.onClickSaveAsQRC = {
            if(binding?.view?.objects!!.isNotEmpty()) {
                var code = QRCDecoder().encode(binding?.view?.objects!!)
                qrcViewer.setup(requireActivity(), 800, 800)
                qrcViewer.code = code
                qrcViewer.show(0, 0, Gravity.CENTER)
            }

            canvasViewer.setup(requireActivity(),1000,1000)
            canvasViewer.canvas.getObjectsFromCode(QRCDecoder().encode(binding?.view?.objects!!))
            canvasViewer.canvas.invalidate()
            canvasViewer.show(0,0,Gravity.CENTER)
        }
        cardMenuPopupWindow.onClickSaveAsImageButton = {
            println("saveAsImage")
            binding?.view?.saveInGallery()
        }
        binding?.buttonPalochki?.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                cardMenuPopupWindow.setup(requireActivity(),
                    R.layout.card_menu_popup_window,
                    700,
                    1000)
                cardMenuPopupWindow.show(0, 275, Gravity.TOP or Gravity.CENTER_HORIZONTAL)
            }
            else{
                val appPerms = arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                activityResultLauncher.launch(appPerms)
            }
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