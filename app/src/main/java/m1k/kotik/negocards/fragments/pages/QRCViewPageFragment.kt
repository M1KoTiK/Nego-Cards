package m1k.kotik.negocards.fragments.pages

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import m1k.kotik.negocards.databinding.FragmentQrcViewPageBinding
import m1k.kotik.negocards.model.QR.QRCreator


class QRCViewPageFragment : Fragment() {
    private var binding: FragmentQrcViewPageBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentQrcViewPageBinding.inflate(inflater,container,false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var QRC = QRCreator()
        binding?.imageView2?.setImageBitmap(QRC.getQRCToBitmap("Hello)"))
        binding?.editTextTextPersonName4?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (!binding?.editTextTextPersonName4?.text.toString().trim().isEmpty()){
                val bitmapimage = QRC.getQRCToBitmap(binding?.editTextTextPersonName4?.text.toString())
                binding?.imageView2?.setImageBitmap(bitmapimage)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

}