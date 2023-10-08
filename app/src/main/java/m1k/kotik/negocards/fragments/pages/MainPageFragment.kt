package m1k.kotik.negocards.fragments.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.canvas_objects.CanvasObject
import m1k.kotik.negocards.data.serialization.DefaultParser
import m1k.kotik.negocards.data.serialization.serializer.DefaultSerializer
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
    inline fun <reified T: Any> Any.cast(): T{
        return this as T
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//Проверка парсера
        var  parser = DefaultParser()
        class TestCanvasObject: CanvasObject(){
            override val key: String
                get() = "co"

        }
        var testObject =  TestCanvasObject()

        var serializator = DefaultSerializer(
            mutableMapOf(
            "co" to TestCanvasObject::class
            )
        )
        //val testString = serializator.serialize(testObject)
        //println(testString)
        val testObject2 = serializator.deserialize<TestCanvasObject>("co:h(0)w(0)x(0)y(0);co:h(0)w(0)x(0)y(0)")
        println(serializator.serialize(testObject!!))


        navController = binding?.root?.findNavController()!!
        binding?.imageView9?.setOnClickListener {
            navController.navigate(R.id.scannerPageFragment)
        }
        binding?.imageView21?.setOnClickListener {
            navController.navigate(R.id.choiceTypeQRCFragment)
        }
    }
}