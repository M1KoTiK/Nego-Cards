package m1k.kotik.negocards.fragments.pages

import android.graphics.Color
import android.graphics.fonts.Font
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button;
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject
import m1k.kotik.negocards.data.canvas_qrc.model.ShapeObject
import m1k.kotik.negocards.data.canvas_qrc.model.ShapeType
import m1k.kotik.negocards.data.canvas_qrc.model.TextObject
import m1k.kotik.negocards.databinding.FragmentCreateCanvasQRCBinding

class CreateCanvasQRCFragment : Fragment() {
    private var binding: FragmentCreateCanvasQRCBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateCanvasQRCBinding.inflate(inflater,container,false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cObj = mutableListOf<CanvasObject>(
            TextObject("Пайсикет",-181818, 40,300,250),
            TextObject("text2",-243434, 60,200,420),
            ShapeObject(ShapeType.Rect, -32323,500,200, 100,200,0,0, false),
            ShapeObject(ShapeType.Oval,-43434,700,200, 100,100,0,0,false),
            ShapeObject(ShapeType.Quadril,-181818,30,20,0,0,0,0,false,
                30,20,
                90,40,
                70,70,
                120,90
            )

        )
        binding?.button5?.setOnClickListener {
            binding?.view?.setCanvasObjects(cObj)
            binding?.view?.invalidate()
        }

    }
}