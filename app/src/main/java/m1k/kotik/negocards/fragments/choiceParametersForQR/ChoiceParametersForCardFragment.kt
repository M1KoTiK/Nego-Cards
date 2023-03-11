package m1k.kotik.negocards.fragments.choiceParametersForQR

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import m1k.kotik.negocards.databinding.FragmentChoiceParametersForCardBinding

class ChoiceParametersForCardFragment : Fragment() {
    private var binding: FragmentChoiceParametersForCardBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChoiceParametersForCardBinding.inflate(inflater,container,false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}