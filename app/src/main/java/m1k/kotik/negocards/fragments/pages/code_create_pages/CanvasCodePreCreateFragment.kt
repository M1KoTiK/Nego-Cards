package m1k.kotik.negocards.fragments.pages.code_create_pages

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.animation.doOnEnd
import androidx.core.view.marginTop
import m1k.kotik.negocards.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val types = resources.getStringArray(R.array.canvas_config_size)
        val arrayAdapter = ArrayAdapter(requireActivity(),R.layout.dropdown_item,types)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        binding.autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, id ->
            if(selectedItemPosition != position){
                selectedItemPosition = position
                if(customConfigSizePanelIsShow){
                    val containerForSizeInCanvas = binding.customCanvasConfigSizeContainer

                    val containerForAdditionSettingsInCanvas = binding.containerForAdditionSettingsInCanvas as View
                    val lp = containerForAdditionSettingsInCanvas.layoutParams as ViewGroup.MarginLayoutParams
                    val initialMargin = lp.topMargin
                    val marginAnimator = ValueAnimator.ofInt(initialMargin, initialMargin - 200)
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
                        val marginAnimator = ValueAnimator.ofInt(initialMargin, initialMargin + 200)
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
                    if(height.toIntOrNull() != null && height.toInt() > 150 && height.toInt() <= 1200){

                        canvasHeightChangeWithAnimation(height.toInt(),180)
                    }
                }
            })
            binding.canvasConfigWidth.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val width = binding.canvasConfigWidth.text.toString()
                    if (width.toIntOrNull() != null && width.toInt() >= 150 && width.toInt() <= 1200) {
                        canvasWidthChangeWithAnimation(width.toInt(),180)
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