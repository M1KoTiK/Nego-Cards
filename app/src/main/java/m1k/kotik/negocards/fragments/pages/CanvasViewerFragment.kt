package m1k.kotik.negocards.fragments.pages

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import m1k.kotik.negocards.R
import m1k.kotik.negocards.custom_views.windows.stylized_window.FloatingStylizedWindow
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

    lateinit var floatingStylizedWindow: FloatingStylizedWindow
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        floatingStylizedWindow = FloatingStylizedWindow(requireContext(), R.layout.qrc_viewer_popup)
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
            floatingStylizedWindow.show(0,0, 1050,1100, Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
            floatingStylizedWindow.windowContentViewGroup.also {
                it.background =
                ResourcesCompat.getDrawable(resources, R.drawable.rounded_square_15, null)
                it.backgroundTintList = ColorStateList.valueOf(0xF5252525.toInt())
            }
            floatingStylizedWindow.header = "Кул окошечко"
        }
    }

    override fun onDestroyView() {
        floatingStylizedWindow.close()
        super.onDestroyView()
    }

}
