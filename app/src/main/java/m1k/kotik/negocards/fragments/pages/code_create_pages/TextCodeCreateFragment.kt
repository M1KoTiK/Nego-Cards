package m1k.kotik.negocards.fragments.pages.code_create_pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import m1k.kotik.negocards.R
import m1k.kotik.negocards.databinding.FragmentCanvasCodeCreateBinding
import m1k.kotik.negocards.databinding.FragmentCanvasCodePreCreateBinding
import m1k.kotik.negocards.databinding.FragmentTextCodeCreateBinding

class TextCodeCreateFragment : Fragment() {

    lateinit var binding: FragmentTextCodeCreateBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTextCodeCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}