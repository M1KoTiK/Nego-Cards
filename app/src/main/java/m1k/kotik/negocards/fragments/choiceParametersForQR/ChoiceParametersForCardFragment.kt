package m1k.kotik.negocards.fragments.choiceParametersForQR

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import m1k.kotik.negocards.R
import m1k.kotik.negocards.databinding.FragmentChoiceParametersForCardBinding
import m1k.kotik.negocards.fragments.pages.CreateCanvasQRCFragment

class ChoiceParametersForCardFragment : Fragment() {
    lateinit var navController: NavController
    private var binding: FragmentChoiceParametersForCardBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentChoiceParametersForCardBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = binding?.root?.findNavController()!!
        binding?.button4?.setOnClickListener {
            navController.navigate(R.id.createCanvasQRCFragment)

        }
    }
}