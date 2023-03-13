package m1k.kotik.negocards.fragments.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import m1k.kotik.negocards.data.canvas_qrc.model.*
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.QadrilShape
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
        )
        binding?.button5?.setOnClickListener {
            binding?.view?.setCanvasObjects(cObj)
            binding?.view?.invalidate()
        }
        binding?.textView3?.setOnClickListener {
            val sh = ShapeObject(ShapeType.Line,200,100,100,200)
            binding?.textView3?.text = sh.encode()
        }

    }
}