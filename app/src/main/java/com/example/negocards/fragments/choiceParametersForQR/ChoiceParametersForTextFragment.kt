package com.example.negocards.fragments.choiceParametersForQR

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.negocards.R
import com.example.negocards.databinding.FragmentChoiceParametersForTextBinding
import com.example.negocards.model.QR.QRCreator

class ChoiceParametersForTextFragment : Fragment() {
    val START_PAGE_POSITION = 0
    lateinit var navController : NavController
    private var selectedItemPosition : Int = START_PAGE_POSITION
    private var binding: FragmentChoiceParametersForTextBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChoiceParametersForTextBinding.inflate(inflater,container,false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = binding?.root?.findNavController()!!
    }

    override fun onResume() {
        super.onResume()
        dropDownSetup()
    }
    private fun dropDownSetup(){

        val types = resources.getStringArray(R.array.type_text_qrc)
        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.dropdown_item,types)
        binding?.autoCompleteTextView?.setAdapter(arrayAdapter)
        binding?.autoCompleteTextView?.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, id ->
            if(selectedItemPosition != position){
                selectedItemPosition = position
                //Toast.makeText(requireActivity(), "Selected: $position", Toast.LENGTH_SHORT).show()
                when(position){
                    0->{

                    }
                }

            }
            //Получение текстового представления
            //val selectedItem = parent.getItemAtPosition(position).toString()
        }
    }
}