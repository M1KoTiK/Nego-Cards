package m1k.kotik.negocards.fragments.pages.code_create_pages

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.animation.doOnEnd
import m1k.kotik.negocards.R
import m1k.kotik.negocards.custom_views.color_picker.HueAndSaturationCirclePicker
import m1k.kotik.negocards.custom_views.sliders.Slider
import m1k.kotik.negocards.custom_views.windows.stylized_window.FloatingStylizedWindow
import m1k.kotik.negocards.databinding.FragmentCanvasCodePreCreateBinding

class CanvasCodePreCreateFragment : Fragment() {

    lateinit var binding: FragmentCanvasCodePreCreateBinding
    var customConfigSizePanelIsShow = false
    var selectedItemPosition = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCanvasCodePreCreateBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun setInitColor(){
        var array = floatArrayOf(0f,0f,0f)
        Color.colorToHSV(Color.parseColor("#F9F9F9"),array)
        hue = array[0]
        saturation = array[1]
        value = array[2]
        updateColor()
    }

    var windowX = 0
    var windowY = 0
    var hue: Float = 0f
    var saturation:Float = 0f
    var value:Float = 1f
    fun updateColor(){
        val color = Color.HSVToColor(255, floatArrayOf(hue, saturation, value))
        binding.colorForCanvasPickButton.backgroundTintList = ColorStateList.valueOf(color)
        binding.canvasConfig.canvasColor = color

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val types = resources.getStringArray(R.array.canvas_config_size)
        val arrayAdapter = ArrayAdapter(requireActivity(),R.layout.dropdown_item,types)
        setInitColor()
        binding.canvasConfigRoundedValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val corner = binding.canvasConfigRoundedValue.text.toString()
                if(corner.toIntOrNull()!=null && corner.toInt() >= 0 )
                    binding.canvasConfig.canvasCorner = corner.toInt()
            }
        })
        binding.canvasConfigWidth.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable){}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val width = binding.canvasConfigWidth.text.toString()
                if(width.toIntOrNull() != null && width.toInt() > 0 && width.toInt() <= 1500){

                    canvasWidthChangeWithAnimation(width.toInt(),180)
                }
            }
        })
        val colorPickerWindow: FloatingStylizedWindow = FloatingStylizedWindow(requireActivity(), R.layout.color_picker).also {
            it.header = "Выбор цвета"
        }
        colorPickerWindow.contentView.findViewById<Slider>(R.id.slider_value_color_window).onSliderChangeValue = {
            value = it
            updateColor()
        }

        var colorCirclePicker = colorPickerWindow.contentView.findViewById<HueAndSaturationCirclePicker>(R.id.hueAndSaturationCirclePicker)
        colorCirclePicker.onSelecting = { hue, saturation ->
            this.hue = hue
            this.saturation = saturation
            updateColor()
        }

        colorPickerWindow.onClose = {
            windowX = colorPickerWindow.windowParameters.x
            windowY = colorPickerWindow.windowParameters.y
        }
        binding.colorForCanvasPickButton.setOnClickListener {
            if(!colorPickerWindow.isWindowOpen) {
                colorPickerWindow.show(
                    windowX,
                    windowY,
                    600,
                    800,
                    Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
                )
            }
        }
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        binding.autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, id ->
            if(selectedItemPosition != position){
                selectedItemPosition = position
                if(customConfigSizePanelIsShow){
                    val containerForSizeInCanvas = binding.customCanvasConfigSizeContainer

                    val containerForAdditionSettingsInCanvas = binding.containerForAdditionSettingsInCanvas as View
                    val lp = containerForAdditionSettingsInCanvas.layoutParams as ViewGroup.MarginLayoutParams
                    val initialMargin = lp.topMargin
                    val marginAnimator = ValueAnimator.ofInt(initialMargin, initialMargin - 205)
                    marginAnimator.addUpdateListener {
                        lp.topMargin = it.animatedValue as Int
                        containerForAdditionSettingsInCanvas.requestLayout()
                        containerForAdditionSettingsInCanvas.invalidate()
                    }
                    marginAnimator.duration = 300
                    marginAnimator.start()

                    val widthAnimator = ObjectAnimator.ofFloat(containerForSizeInCanvas, "scaleX", 1f, 0f)
                    widthAnimator.duration = 500
                    widthAnimator.doOnEnd {
                    }
                    widthAnimator.start()

                    val heightAnimator = ObjectAnimator.ofFloat(containerForSizeInCanvas, "scaleY", 1f, 0f)
                    heightAnimator.duration = 600
                    heightAnimator.start()
                    customConfigSizePanelIsShow = false
                    binding.canvasConfigHeight.clearFocus()
                    binding.canvasConfigWidth.clearFocus()
                    val imm =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
                }
                //Toast.makeText(requireActivity(), "Selected: $position", Toast.LENGTH_SHORT).show()
                when(binding.autoCompleteTextView.text.toString()){
                    "Маленький"-> canvasSizeChangeWithAnimation(600, 350, 180)
                    "Средний"-> canvasSizeChangeWithAnimation(800, 600, 180)
                    "Большой"-> canvasSizeChangeWithAnimation(1000, 700, 180)
                    "Кастомный"->{
                        val containerForSizeInCanvas = binding.customCanvasConfigSizeContainer

                        val containerForAdditionSettingsInCanvas = binding.containerForAdditionSettingsInCanvas as View
                        val lp = containerForAdditionSettingsInCanvas.layoutParams as ViewGroup.MarginLayoutParams
                        val initialMargin = lp.topMargin
                        val marginAnimator = ValueAnimator.ofInt(initialMargin, initialMargin + 205)
                        marginAnimator.addUpdateListener {
                            lp.topMargin = it.animatedValue as Int
                            containerForAdditionSettingsInCanvas.requestLayout()
                            containerForAdditionSettingsInCanvas.invalidate()
                        }
                        marginAnimator.duration = 300
                        marginAnimator.start()
                        val widthAnimator = ObjectAnimator.ofFloat(containerForSizeInCanvas, "scaleX", 0f, 1f)
                        widthAnimator.duration = 500
                        widthAnimator.doOnEnd {
                        }
                        widthAnimator.start()

                        val heightAnimator = ObjectAnimator.ofFloat(containerForSizeInCanvas, "scaleY", 0f, 1f)
                        heightAnimator.duration = 600
                        heightAnimator.start()
                        customConfigSizePanelIsShow = true
                    }
                }
            }
            binding.canvasConfigHeight.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable){}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val height = binding.canvasConfigHeight.text.toString()
                    if(height.toIntOrNull() != null && height.toInt() > 0 && height.toInt() <= 1500){

                        canvasHeightChangeWithAnimation(height.toInt(),180)
                    }
                }
            })



            //Получение текстового представления
            //val selectedItem = parent.getItemAtPosition(position).toString()
        }
    }
    fun canvasSizeChangeWithAnimation(targetWidth: Int, targetHeight:Int, duration:Long){
        canvasHeightChangeWithAnimation(targetHeight,duration)
        canvasWidthChangeWithAnimation(targetWidth,duration)
    }

    fun canvasHeightChangeWithAnimation(targetHeight:Int, duration: Long){
        val initialHeight = binding.canvasConfig.canvasHeight
        val heightAnimator = ValueAnimator.ofInt(initialHeight, targetHeight)
        heightAnimator.addUpdateListener {
            binding.canvasConfig.canvasHeight = it.animatedValue as Int
        }
        heightAnimator.duration = duration
        heightAnimator.start()
    }
    fun canvasWidthChangeWithAnimation(targetWidth:Int, duration: Long){
        val initialWidth = binding.canvasConfig.canvasWidth
        val widthAnimator = ValueAnimator.ofInt(initialWidth, targetWidth)
        widthAnimator.addUpdateListener {
            binding.canvasConfig.canvasWidth = it.animatedValue as Int
        }
        widthAnimator.duration = duration
        widthAnimator.start()
    }
}