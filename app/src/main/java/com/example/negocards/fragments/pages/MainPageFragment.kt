package com.example.negocards.fragments.pages

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.negocards.R
import com.example.negocards.databinding.FragmentMainPageBinding
import com.example.negocards.model.QR.QRCreator
import me.ibrahimsn.lib.SmoothBottomBar


class MainPageFragment : Fragment() {
    private lateinit var navController: NavController
    private var binding: FragmentMainPageBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainPageBinding.inflate(inflater,container,false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = binding?.root?.findNavController()!!
        binding?.button2?.setOnClickListener {
            navController.navigate(R.id.scannerPageFragment)
        }
        binding?.button3?.setOnClickListener {
            navController.navigate(R.id.QRCViewPageFragment)
        }
    }
}