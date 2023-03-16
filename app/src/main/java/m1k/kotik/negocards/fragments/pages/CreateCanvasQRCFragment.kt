package m1k.kotik.negocards.fragments.pages

import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import m1k.kotik.negocards.data.canvas_qrc.model.*
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.QadrilShape
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectRShape
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject.ObjectType
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.ArcShape
import m1k.kotik.negocards.databinding.FragmentCreateCanvasQRCBinding

class CreateCanvasQRCFragment : Fragment() {
    private var binding: FragmentCreateCanvasQRCBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateCanvasQRCBinding.inflate(inflater,container,false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val testObj = TextObject("Пайс",60 ,"FF181818" ,180,250)
        val cObj = mutableListOf<CanvasObject>(
            TextObject("Пайс",60 ,"FF181818" ,180,250),
            TextObject("Пайс",60 ,"FF909090" ,300,450),
            ShapeObject(ObjectType.Shape.Oval(),200,100,100,100,"FF309030"),
            ArcShape(30,270,true,400,200,100,100,
                "FF181818",Paint.Style.FILL)
        )
        binding?.button5?.setOnClickListener {
            binding?.view?.setCanvasObjects(cObj)
            binding?.view?.invalidate()
        }
        binding?.textView3?.setOnClickListener {
            val cm =  CanvasModel(RectRShape(12,12,12,12,12,12,"c",Paint.Style.FILL),cObj)
            binding?.textView3?.setText(cm.encode())
        }

    }
}