package com.example.negocards.fragments.choiceParametersForQR

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.negocards.databinding.FragmentChoiceParametersForTextBinding

class ChoiceParametersForTextFragment : Fragment() {
    private var binding: FragmentChoiceParametersForTextBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChoiceParametersForTextBinding.inflate(inflater,container,false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}