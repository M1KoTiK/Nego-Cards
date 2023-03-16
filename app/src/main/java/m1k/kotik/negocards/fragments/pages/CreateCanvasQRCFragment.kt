package m1k.kotik.negocards.fragments.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import m1k.kotik.negocards.data.canvas_qrc.model.*
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject.Tag
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectR
import m1k.kotik.negocards.databinding.FragmentCreateCanvasQRCBinding

class CreateCanvasQRCFragment : Fragment() {
    private var binding: FragmentCreateCanvasQRCBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateCanvasQRCBinding.inflate(inflater,container,false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val testObj = TextObject("Пайс",60 ,"FF181818" ,180,250,Tag.Style.Fill())
        val cObj = mutableListOf<CanvasObject>(
            TextObject("Пайс",60 ,"FF181818" ,180,250, Tag.Style.Stroke()),
            TextObject("Пайс",60 ,"FF909090" ,300,450,Tag.Style.Fill()),
        )
        binding?.button5?.setOnClickListener {
            binding?.view?.setCanvasObjects(cObj)
            binding?.view?.invalidate()
        }
        binding?.textView3?.setOnClickListener {
            var c :String = ""
            for(co in cObj) {
                c = c+ co.encode()
            }
            binding?.textView3?.setText(c)
        }

    }
}