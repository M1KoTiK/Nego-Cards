package m1k.kotik.negocards.fragments.pages

import android.graphics.fonts.Font
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button;
import m1k.kotik.negocards.data.canvas_qrc.model.CanvasObject
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
            TextObject("text1",1, 60,300,250),
            TextObject("text2",2, 120,200,420),
            TextObject("text3",3, 30,50,100)
        )
        binding?.button5?.setOnClickListener {
            binding?.view?.setCanvasObjects(cObj)
            binding?.view?.invalidate()
        }

    }
}