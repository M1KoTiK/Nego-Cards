package m1k.kotik.negocards.fragments.pages

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import m1k.kotik.negocards.R
import m1k.kotik.negocards.custom_views.color_picker.HueAndSaturationCirclePicker
import m1k.kotik.negocards.custom_views.sliders.Slider
import m1k.kotik.negocards.custom_views.toast.showCustomToast
import m1k.kotik.negocards.custom_views.windows.stylized_window.FloatingStylizedWindow
import m1k.kotik.negocards.data.ViewSaver
import m1k.kotik.negocards.data.canvas_qrc.old_govno.parseColorFromString
import m1k.kotik.negocards.data.code.CodeContentType
import m1k.kotik.negocards.data.code.CodeType
import m1k.kotik.negocards.data.code.ScannedCode
import m1k.kotik.negocards.data.code.code_generators.QRCGenerator
import m1k.kotik.negocards.data.date.SimpleDate
import m1k.kotik.negocards.data.recycler_view_adapters.adapter_decorations.SpaceItemDecorator
import m1k.kotik.negocards.data.recycler_view_adapters.code_action.CodeActionAdapter
import m1k.kotik.negocards.databinding.DotedItemBinding
import m1k.kotik.negocards.databinding.FragmentChoiceTypeQrcBinding
import m1k.kotik.negocards.databinding.FragmentCodeEditorBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class CodeEditorFragment : Fragment() {
    lateinit var binding: FragmentCodeEditorBinding
    private lateinit var scannedCode: ScannedCode
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCodeEditorBinding.inflate(inflater,container,false)
        return binding.root
    }
    val cGen = QRCGenerator()
    var codeImage: Bitmap? = null
        set(value) {
            field = value
            binding.CodeViewInCodeEditor.setImageBitmap(codeImage)

        }
    var backgroundColor = parseColorFromString("#FFFFFF")
        set(value) {
            field = value
            cGen.backgroundColor = value
            updateCodeImage()
        }

    var codeColor = parseColorFromString("#181818")
        set(value) {
            field = value
            cGen.codeColor = value
            updateCodeImage()
        }
    fun updateCodeImage(){
        codeImage = cGen.generateCodeBitmap(scannedCode.value)

    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val bundle: Bundle = this.requireArguments()
        val contentType: CodeContentType? = CodeContentType.values().firstOrNull { it.ordinal == bundle.getInt("contentType") }
        val value: String? = bundle.getString("value")
        val date: SimpleDate? = SimpleDate.toSimpleDate(bundle.getString("date") ?: "")
        val codeType: CodeType? = CodeType.values().firstOrNull { it.ordinal == bundle.getInt("codeType") }
        val cGen = QRCGenerator()
        binding.colorForCodePickButton.backgroundTintList = ColorStateList.valueOf(codeColor)
        binding.colorForBackgroundCodePickButton.backgroundTintList = ColorStateList.valueOf(backgroundColor)

        if (contentType != null && value != null && codeType != null && date != null) {
            scannedCode = ScannedCode(codeType, contentType, value, date, true)
            updateCodeImage()

            binding.downloadQrcInCodeEditor.setOnClickListener {
                val codeBitmapImage = ViewSaver.getBitmapFromView(binding.cardViewInCodeEditor)
                ViewSaver.saveBitmapInGallery(codeBitmapImage, requireContext())
                Toast(requireContext()).showCustomToast(
                    "Изображение кода сохранено в галерею",
                    requireContext()
                )
            }

            binding.shareCodeTextInCodeEditor.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, value)
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Поделиться текстом кода"))
            }

            var windowX1: Int = 0
            var windowY1: Int = 0
            binding.colorForBackgroundCodePickButton.setOnClickListener{

                val colorPickerWindow: FloatingStylizedWindow = FloatingStylizedWindow(requireActivity(), R.layout.color_picker).also {
                    it.header = "Выбор цвета фона кода"
                }
                val colorCirclePicker = colorPickerWindow.contentView.findViewById<HueAndSaturationCirclePicker>(R.id.hueAndSaturationCirclePicker)
                val colorSliderValuePicker = colorPickerWindow.contentView.findViewById<Slider>(R.id.slider_value_color_window)

                colorSliderValuePicker.onSliderChangeValue = {
                    backgroundColor =  Color.HSVToColor(255, floatArrayOf(colorCirclePicker.selectedHue, colorCirclePicker.selectedSaturation, it))
                    binding.colorForBackgroundCodePickButton.backgroundTintList = ColorStateList.valueOf(backgroundColor)
                }

                colorCirclePicker.onSelecting = { hue, saturation ->
                    backgroundColor =  Color.HSVToColor(255, floatArrayOf(hue, saturation, colorSliderValuePicker.outputValue ))
                    binding.colorForBackgroundCodePickButton.backgroundTintList = ColorStateList.valueOf(backgroundColor)
                }


                colorPickerWindow.onClose = {
                    windowX1 = colorPickerWindow.windowParameters.x
                    windowY1 = colorPickerWindow.windowParameters.y
                }

                if(!colorPickerWindow.isWindowOpen) {
                    colorPickerWindow.show(
                        windowX1,
                        windowY1,
                        600,
                        800,
                        Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
                    )
                }
            }

            var windowX2: Int = 0
            var windowY2: Int = 0
            binding.colorForCodePickButton.setOnClickListener{

                val colorPickerWindow: FloatingStylizedWindow = FloatingStylizedWindow(requireActivity(), R.layout.color_picker).also {
                    it.header = "Выбор цвета кода"
                }
                val colorCirclePicker = colorPickerWindow.contentView.findViewById<HueAndSaturationCirclePicker>(R.id.hueAndSaturationCirclePicker)
                val colorSliderValuePicker = colorPickerWindow.contentView.findViewById<Slider>(R.id.slider_value_color_window)

                colorSliderValuePicker.onSliderChangeValue = {
                    codeColor =  Color.HSVToColor(255, floatArrayOf(colorCirclePicker.selectedHue, colorCirclePicker.selectedSaturation, it))
                    binding.colorForCodePickButton.backgroundTintList = ColorStateList.valueOf(codeColor)
                }

                colorCirclePicker.onSelecting = { hue, saturation ->
                    codeColor =  Color.HSVToColor(255, floatArrayOf(hue, saturation, colorSliderValuePicker.outputValue ))
                    binding.colorForCodePickButton.backgroundTintList = ColorStateList.valueOf(codeColor)
                }


                colorPickerWindow.onClose = {
                    windowX2 = colorPickerWindow.windowParameters.x
                    windowY2 = colorPickerWindow.windowParameters.y
                }

                if(!colorPickerWindow.isWindowOpen) {
                    colorPickerWindow.show(
                        windowX2,
                        windowY2,
                        600,
                        800,
                        Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
                    )
                }
            }



            binding.shareImageQrcInCodeEditor.setOnClickListener {
                val image = codeImage
                if (image != null) {
                    try {
                        val cachePath = File(requireContext().cacheDir, "images")
                        cachePath.mkdirs() // don't forget to make the directory
                        val stream =
                            FileOutputStream(cachePath.toString() + "/image.png") // overwrites this image every time
                        image.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        stream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    val imagePath = File(requireContext().cacheDir, "images")
                    val newFile = File(imagePath, "image.png")
                    val contentUri: Uri? =
                        FileProvider.getUriForFile(
                            requireContext(),
                            "m1k.kotik.negocards.fileprovider",
                            newFile
                        )

                    if (contentUri != null) {
                        val shareIntent = Intent()
                        shareIntent.action = Intent.ACTION_SEND
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // temp permission for receiving app to read this file
                        shareIntent.setDataAndType(
                            contentUri,
                            requireContext().contentResolver.getType(contentUri)
                        )
                        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
                        startActivity(
                            Intent.createChooser(
                                shareIntent,
                                "Поделиться изображением кода"
                            )
                        )
                    }
                }
            }
        }
    }



}