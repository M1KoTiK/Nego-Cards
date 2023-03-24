package m1k.kotik.negocards.fragments.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import m1k.kotik.negocards.data.canvas_qrc.model.*
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject.Tag
import m1k.kotik.negocards.data.canvas_qrc.model.shapes.RectShape
import m1k.kotik.negocards.databinding.FragmentCreateCanvasQRCBinding

class CreateCanvasQRCFragment : Fragment() {
    private var binding: FragmentCreateCanvasQRCBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateCanvasQRCBinding.inflate(inflater,container,false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val testObj = TextObject("jopa",50,"FF181818",200,100,Tag.Style.Fill())
        val cObj = mutableListOf<CanvasObject>()
        cObj.add(testObj)
        binding?.button5?.setOnClickListener {
            cObj.clear()
            try {
                testObj.applyCode(binding?.textView3?.text.toString())
                cObj.add(testObj)
            }
            catch(e:Exception){
                Toast.makeText(requireActivity(),e.message,Toast.LENGTH_SHORT).show()
            }
            binding?.view?.setCanvasObjects(cObj)
            binding?.view?.invalidate()
        }
    }
}