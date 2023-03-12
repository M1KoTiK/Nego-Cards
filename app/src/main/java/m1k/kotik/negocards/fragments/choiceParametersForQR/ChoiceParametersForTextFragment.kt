package m1k.kotik.negocards.fragments.choiceParametersForQR

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.launch
import m1k.kotik.negocards.R
import m1k.kotik.negocards.databinding.FragmentChoiceParametersForTextBinding
import m1k.kotik.negocards.fragments.choiceParametersForQR.parametersForText.ParamsLocation
import m1k.kotik.negocards.fragments.choiceParametersForQR.parametersForText.ParamsReference
import m1k.kotik.negocards.fragments.choiceParametersForQR.parametersForText.ParamsText
import m1k.kotik.negocards.fragments.choiceParametersForQR.parametersForText.IParamsQRC
import m1k.kotik.negocards.data.QRC.QRCreator


class ChoiceParametersForTextFragment : Fragment() {
    //надо не забыть убрать говнецо))
    val START_PAGE_POSITION = 0
    lateinit var navController: NavController
    private var selectedItemPosition: Int = START_PAGE_POSITION
    private var currentFragment: IParamsQRC = ParamsText()
    private var binding: FragmentChoiceParametersForTextBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentChoiceParametersForTextBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = binding?.root?.findNavController()!!
        replaceFragment(currentFragment)

        /*
        var QRC = QRCreator()
        val bitmapimage = QRC.getQRCToBitmap(binding?.editTextTextPersonName4?.text.toString())
        binding?.imageView2?.setImageBitmap(bitmapimage)
        */
    }

    override fun onResume() {
        super.onResume()
        dropDownSetup()
    }

    private fun dropDownSetup() {

        val types = resources.getStringArray(R.array.type_text_qrc)
        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.dropdown_item, types)
        binding?.autoCompleteTextView?.setAdapter(arrayAdapter)
        binding?.autoCompleteTextView?.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, id ->
                if (selectedItemPosition != position) {
                    selectedItemPosition = position
                    //Toast.makeText(requireActivity(), "Selected: $position", Toast.LENGTH_SHORT).show()
                    when (position) {
                        0 -> {
                            replaceFragment(ParamsText())
                        }
                        1 -> {
                            replaceFragment(ParamsReference())
                        }
                        2 -> {
                            replaceFragment(ParamsLocation())
                        }
                    }

                }
                //Получение текстового представления
                //val selectedItem = parent.getItemAtPosition(position).toString()
            }
    }

    private fun replaceFragment(fragment: IParamsQRC) {
        fragment as Fragment
        val transaction = childFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView3, fragment)
            commit()
        }
        currentFragment = fragment
        viewLifecycleOwner.lifecycleScope.launch {
            currentFragment.QRCValue.filterNot { it.isEmpty() }.collect { qrcString ->
                val QRC = QRCreator()
                val bitmapimage = QRC.getQRCToBitmap(qrcString)
                binding?.imageView?.setImageBitmap(bitmapimage)
            }
        }
    }

}