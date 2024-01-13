package m1k.kotik.negocards.fragments.pages.code_create_pages

import android.animation.ObjectAnimator
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
import m1k.kotik.negocards.R
import m1k.kotik.negocards.databinding.FragmentCanvasCodePreCreateBinding
import m1k.kotik.negocards.fragments.choiceParametersForQR.ChoiceParametersForCardFragment
import m1k.kotik.negocards.fragments.choiceParametersForQR.ChoiceParametersForTextFragment
import m1k.kotik.negocards.fragments.utils_fragment.PlaceholderFragment

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
                    val container = binding.customCanvasConfigSizeContainer
                    val widthAnimator = ObjectAnimator.ofFloat(container, "scaleX", 1f, 0f)
                    widthAnimator.duration = 500
                    widthAnimator.doOnEnd {
                    }
                    widthAnimator.start()

                    val heightAnimator = ObjectAnimator.ofFloat(container, "scaleY", 1f, 0f)
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
                    "Маленький"->{
                        binding.canvasConfig.canvasWidth = 600
                        binding.canvasConfig.canvasHeight = 350
                    }
                    "Средний"->{
                        binding.canvasConfig.canvasWidth = 800
                        binding.canvasConfig.canvasHeight = 600
                    }
                    "Большой"->{
                        binding.canvasConfig.canvasWidth = 1000
                        binding.canvasConfig.canvasHeight = 700
                    }
                    "Кастомный"->{
                        val container = binding.customCanvasConfigSizeContainer
                        val widthAnimator = ObjectAnimator.ofFloat(container, "scaleX", 0f, 1f)
                        widthAnimator.duration = 500
                        widthAnimator.doOnEnd {
                        }
                        widthAnimator.start()

                        val heightAnimator = ObjectAnimator.ofFloat(container, "scaleY", 0f, 1f)
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
                    if(height.toIntOrNull() != null && before < count){

                        binding.canvasConfig.canvasHeight = height.toInt()
                    }
                }
            })
            binding.canvasConfigWidth.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val width = binding.canvasConfigWidth.text.toString()
                    if (width.toIntOrNull() != null && before < count) {
                        binding.canvasConfig.canvasWidth = width.toInt()
                    }
                }
            })

            //Получение текстового представления
            //val selectedItem = parent.getItemAtPosition(position).toString()
        }
    }
}