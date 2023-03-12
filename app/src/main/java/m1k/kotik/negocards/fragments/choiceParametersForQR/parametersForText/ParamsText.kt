package m1k.kotik.negocards.fragments.choiceParametersForQR.parametersForText

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import m1k.kotik.negocards.databinding.FragmentParamsTextBinding

class ParamsText : Fragment(), IParamsQRC {
    val PARAMS_TAG: String = "tp:text;"
    private var binding: FragmentParamsTextBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentParamsTextBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListenerTextChange(binding?.editTextTextPersonName7!!) {
            val text = binding?.editTextTextPersonName7?.text.toString()
            setQRCValue(text)
        }
    }

    override val QRCValue = MutableStateFlow<String>("")

    private fun setQRCValue(text: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            QRCValue.emit(text)
        }
    }

    private fun setListenerTextChange(editText: EditText, action: () -> Unit) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                action()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}