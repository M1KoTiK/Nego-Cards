package m1k.kotik.negocards.fragments.pages

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import m1k.kotik.negocards.R
import m1k.kotik.negocards.custom_views.windows.FloatingWindow
import m1k.kotik.negocards.data.canvas_qrc.canvas_serialization.CanvasSerialization
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
    lateinit var floatingWindow:FloatingWindow
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        floatingWindow = FloatingWindow(requireContext(), R.layout.input_text_popup)
        binding.canvasViewer.setObjects(
            listOf(
                Rectangle(12, paint = Paint().also { it.color = Color.GREEN }),
                Rectangle(500, 150, paint = Paint().also { it.color = Color.RED }),
                Rectangle(200, 50,paint = Paint().also { it.color = Color.BLUE })
            )
        )
        binding.canvasViewer.onCurrentSelectedObjectChange = {

            val listObj = binding.canvasViewer.listCurrentSelectedObjects
            println(CanvasSerialization.canvasSerializer.serialize(listObj))

        }
        binding.valueSlider.onSliderChangeValue = {
            binding.canvasViewer.canvasZoom = it
            println(it)
        }

        binding.button3.setOnClickListener {
            floatingWindow.show(-50,10, 1050,500, Gravity.TOP or Gravity.LEFT)
        }

    }

    override fun onDestroyView() {
        floatingWindow.close()
        super.onDestroyView()
    }

}