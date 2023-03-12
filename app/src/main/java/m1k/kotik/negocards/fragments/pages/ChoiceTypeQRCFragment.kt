package m1k.kotik.negocards.fragments.pages

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import m1k.kotik.negocards.R
import m1k.kotik.negocards.databinding.FragmentChoiceTypeQrcBinding
import m1k.kotik.negocards.fragments.choiceParametersForQR.ChoiceParametersForCardFragment
import m1k.kotik.negocards.fragments.choiceParametersForQR.ChoiceParametersForTextFragment


class ChoiceTypeQRCFragment : Fragment() {
    val START_PAGE_POSITION : Int = 0
    private var selectedItemPosition : Int = START_PAGE_POSITION
    private var binding: FragmentChoiceTypeQrcBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChoiceTypeQrcBinding.inflate(inflater,container,false)
        return binding!!.root
    }
    override fun onResume() {
        super.onResume()
        dropDownSetup()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
    private fun dropDownSetup(){

        val types = resources.getStringArray(R.array.type_qrc)
        val arrayAdapter = ArrayAdapter(requireActivity(),R.layout.dropdown_item,types)
        binding?.autoCompleteTextView?.setAdapter(arrayAdapter)
        binding?.autoCompleteTextView?.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, id ->
            if(selectedItemPosition != position){
                selectedItemPosition = position
                //Toast.makeText(requireActivity(), "Selected: $position", Toast.LENGTH_SHORT).show()
                when(position){
                    0->{
                        replaceFragment(ChoiceParametersForCardFragment())
                    }
                    1->{
                        replaceFragment(ChoiceParametersForTextFragment())
                    }
                }

            }
            //Получение текстового представления
            //val selectedItem = parent.getItemAtPosition(position).toString()
        }
    }
    private fun replaceFragment(fragment: Fragment){
        val transaction = childFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerForChoiceTypeQRC,fragment)
            commit()
        }
    }
}



