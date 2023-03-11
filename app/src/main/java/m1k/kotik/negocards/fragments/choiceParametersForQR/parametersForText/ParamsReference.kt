package m1k.kotik.negocards.fragments.choiceParametersForQR.parametersForText

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import m1k.kotik.negocards.R
import m1k.kotik.negocards.databinding.FragmentParamsReferenceBinding
import m1k.kotik.negocards.databinding.FragmentParamsTextBinding
import m1k.kotik.negocards.model.IParamsQRC

class ParamsReference : Fragment(), IParamsQRC {
    //и здесь насрано
    val PARAMS_TAG: String = "tp:ref;"
    val REF_TAG:String = "r:"
    private var ref:String = ""
    private var binding: FragmentParamsReferenceBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentParamsReferenceBinding.inflate(inflater,container,false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListenerTextChange(binding?.editTextTextPersonName8!!,){
            ref = binding?.editTextTextPersonName8?.text.toString()
        }
    }
    override val QRCValue: String
        get() = "$PARAMS_TAG+$REF_TAG+$ref+;"

    private fun setListenerTextChange(editText:EditText, action:()->Unit){
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                action()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}