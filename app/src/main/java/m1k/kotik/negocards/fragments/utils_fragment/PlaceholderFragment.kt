package m1k.kotik.negocards.fragments.utils_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import m1k.kotik.negocards.R
import m1k.kotik.negocards.databinding.FragmentMainPageBinding
import m1k.kotik.negocards.databinding.FragmentPlaceholderBinding
import m1k.kotik.negocards.databinding.FragmentScannerPageBinding

class PlaceholderFragment : Fragment() {
    private var binding: FragmentPlaceholderBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPlaceholderBinding.inflate(inflater, container, false)
        return binding!!.root
    }


}