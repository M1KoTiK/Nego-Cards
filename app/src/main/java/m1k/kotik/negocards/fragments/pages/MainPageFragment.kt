package m1k.kotik.negocards.fragments.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject
import m1k.kotik.negocards.data.serialization.TestParser
import m1k.kotik.negocards.data.serialization.serializationObject.TestSerializeObject
import m1k.kotik.negocards.databinding.FragmentMainPageBinding

class MainPageFragment: Fragment() {
    private lateinit var navController: NavController
    private var binding: FragmentMainPageBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var  parser = TestParser()
        var testObject = TestSerializeObject()
        val map = parser.parseString("text\"valueText\"int(10)", testObject)
        println(map.keys.toString())
        map.values.forEach { it-> println(" type: ${it.type} value: ${it.value}") }



        navController = binding?.root?.findNavController()!!
        binding?.button2?.setOnClickListener {
            navController.navigate(R.id.scannerPageFragment)
        }
        binding?.button3?.setOnClickListener {
            navController.navigate(R.id.choiceTypeQRCFragment)
        }
    }
}