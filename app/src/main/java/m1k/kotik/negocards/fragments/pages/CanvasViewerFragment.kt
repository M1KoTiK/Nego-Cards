package m1k.kotik.negocards.fragments.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Rectangle
import m1k.kotik.negocards.databinding.FragmentCanvasViewerBinding

class CanvasViewerFragment: Fragment() {
    private lateinit var binding: FragmentCanvasViewerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCanvasViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.canvasViewer.setObjects(
            listOf(
                Rectangle(12),
                Rectangle(500, 150),
                Rectangle(200, 50)
            )
        )
        binding.valueSliderPicker.onSliderValueChange = {
            binding.canvasViewer.canvasZoom = it
            println(it)
        }
    }
}
