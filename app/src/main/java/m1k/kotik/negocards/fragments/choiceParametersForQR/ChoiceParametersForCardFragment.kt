package m1k.kotik.negocards.fragments.choiceParametersForQR

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import m1k.kotik.negocards.R
import m1k.kotik.negocards.databinding.FragmentChoiceParametersForCardBinding

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
            if(binding?.editTextTextPersonName5?.text?.toString()?.toIntOrNull() != null &&
                binding?.editTextTextPersonName6?.text?.toString()?.toIntOrNull() != null)
            {
            val bundle = Bundle()
            bundle.putInt("width",binding?.editTextTextPersonName5?.text.toString().toInt())
            bundle.putInt("height",binding?.editTextTextPersonName6?.text.toString().toInt())
            navController.navigate(R.id.createCanvasQRCFragment, bundle)
            }
            else{
                Toast.makeText(requireActivity(),"Что-то заполнено неправильно",Toast.LENGTH_SHORT).show()
            }

        }
    }
}