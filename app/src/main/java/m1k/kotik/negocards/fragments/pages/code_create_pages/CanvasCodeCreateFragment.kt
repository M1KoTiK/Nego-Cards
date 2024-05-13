package m1k.kotik.negocards.fragments.pages.code_create_pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Rectangle
import m1k.kotik.negocards.databinding.FragmentCanvasCodeCreateBinding


class CanvasCodeCreateFragment : Fragment() {
    lateinit var binding: FragmentCanvasCodeCreateBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCanvasCodeCreateBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.canvasEditor.addObject(Rectangle().also {
            it.x = 50
            it.y = 50
            it.isSelected = true})
    }
}