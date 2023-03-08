package com.example.negocards.fragments.pages

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.example.negocards.R
import com.example.negocards.databinding.FragmentChoiceTypeQrcBinding


class ChoiceTypeQRCFragment : Fragment() {
    private var binding: FragmentChoiceTypeQrcBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChoiceTypeQrcBinding.inflate(inflater,container,false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        val types = resources.getStringArray(R.array.type_qrc)
        val arrayAdapter = ArrayAdapter(requireActivity(),R.layout.dropdown_item,types)
        binding?.autoCompleteTextView?.setAdapter(arrayAdapter)
    }
}

