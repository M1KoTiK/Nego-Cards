package m1k.kotik.negocards.fragments.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.serialization.DefaultParser
import m1k.kotik.negocards.data.serialization.serializationObject.TestSerializeObject
import m1k.kotik.negocards.data.serialization.serializer.TestSerializer
import m1k.kotik.negocards.databinding.FragmentMainPageBinding
import kotlin.reflect.full.createType
import kotlin.reflect.KType
import kotlin.reflect.KClass
import kotlin.reflect.cast
import kotlin.reflect.full.createInstance
import kotlin.reflect.javaType
import kotlin.reflect.jvm.javaType
import kotlin.reflect.jvm.jvmErasure


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
        var testObject = TestSerializeObject()

        var serializator = TestSerializer()
        var serObject = serializator.deserialize<TestSerializeObject>("sqint(1214)list[1,3]text\"1256\"")
        println( serializator.serialize(serObject!!))



        navController = binding?.root?.findNavController()!!
        binding?.button2?.setOnClickListener {
            navController.navigate(R.id.scannerPageFragment)
        }
        binding?.button3?.setOnClickListener {
            navController.navigate(R.id.choiceTypeQRCFragment)
        }
    }
}