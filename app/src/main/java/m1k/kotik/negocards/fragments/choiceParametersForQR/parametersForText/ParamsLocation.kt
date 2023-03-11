package m1k.kotik.negocards.fragments.choiceParametersForQR.parametersForText

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import m1k.kotik.negocards.databinding.FragmentParamsLocationBinding
import m1k.kotik.negocards.model.IParamsQRC

class ParamsLocation : Fragment(), IParamsQRC {
    //пока что тут потом надо убрать)))
    val PARAMS_TAG: String = "tp:loc;"
    val WIDTH_TAG:String = "w:"
    val LONGITUDE_TAG:String = "lt:"
    private var Width :String = ""
    private var Longitude :String = ""
    private var binding: FragmentParamsLocationBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentParamsLocationBinding.inflate(inflater,container,false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListenerTextChange(
            binding?.editTextTextPersonName9!!,) {
            Width = binding?.editTextTextPersonName9!!.text.toString()
        }
        setListenerTextChange(
            binding?.editTextTextPersonName10!!,) {
            Longitude = binding?.editTextTextPersonName10!!.text.toString()
        }
    }


    override val QRCValue: String
    get() = "$PARAMS_TAG+$WIDTH_TAG+$Width+;+$LONGITUDE_TAG+$Longitude+;"

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
