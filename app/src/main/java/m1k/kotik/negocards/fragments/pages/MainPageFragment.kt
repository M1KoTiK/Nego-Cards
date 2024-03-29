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
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Rectangle
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.RoundRectangle
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
        val testCanvasObject = RoundRectangle(0,0,900,600, 7,7, Paint().also { it.color = "#FF111111".toColorInt(); it.style = Paint.Style.FILL })
        var serializator = CanvasSerialization.canvasSerializer
        val testString = serializator.serialize(mutableListOf(testCanvasObject,testCanvasObject))
        println(testString)
//        val testDesObj = serializator.deserialize<Rectangle>(testString!!)
//        println( testDesObj!!.count())
//        val serDesObject = serializator.serialize(testDesObj!!)
//        println(serDesObject)

        navController = binding?.root?.findNavController()!!
        binding?.imageView9?.setOnClickListener {
            navController.navigate(R.id.scannerPageFragment)
        }
        binding?.testBtn?.setOnClickListener {
            //navController.navigate(R.id.canvasViewerFragment)
        }

        binding?.imageView21?.setOnClickListener {
            //choiceTypeQRCFragment
            navController.navigate(R.id.choiceCodeTypeFragment)
        }
    }
}