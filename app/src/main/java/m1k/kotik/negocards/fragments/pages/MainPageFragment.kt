package m1k.kotik.negocards.fragments.pages

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.canvas_serialization.CanvasSerialization
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.CanvasShape
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Rectangle
import m1k.kotik.negocards.data.canvas_qrc.old_govno.canvas_object_types.ShapeObject
import m1k.kotik.negocards.data.serialization.parser.DefaultParser
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//Проверка парсера
        var  parser = DefaultParser()
        val testCanvasObject = Rectangle(12,10,100,200, Paint())
        val serializtionHelper = CanvasSerialization()
        var serializator = serializtionHelper.canvasSerializer
        val testString = serializator.serialize(testCanvasObject)
        println(testString)
        serializator.deserialize<Rectangle>(testString)



        navController = binding?.root?.findNavController()!!
        binding?.imageView9?.setOnClickListener {
            navController.navigate(R.id.scannerPageFragment)
        }
        binding?.imageView21?.setOnClickListener {
            navController.navigate(R.id.choiceTypeQRCFragment)
        }
    }
}