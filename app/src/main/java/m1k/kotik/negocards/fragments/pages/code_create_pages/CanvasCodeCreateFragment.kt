package m1k.kotik.negocards.fragments.pages.code_create_pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Oval
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Rectangle
import m1k.kotik.negocards.data.recycler_view_adapters.adapter_decorations.SpaceItemDecorator
import m1k.kotik.negocards.data.recycler_view_adapters.adapter_decorations.SpaceItemDecoratorHorizontal
import m1k.kotik.negocards.data.recycler_view_adapters.canvas_object_types.CanvasObjectTypesAdapter
import m1k.kotik.negocards.data.recycler_view_adapters.canvas_object_types.CanvasObjectTypesViewModel
import m1k.kotik.negocards.databinding.FragmentCanvasCodeCreateBinding


class CanvasCodeCreateFragment : Fragment() {
    lateinit var navController: NavController
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
        navController = binding.root.findNavController()
        val adapterCanvasObjectTypes = CanvasObjectTypesAdapter(requireContext(), listOf(
            CanvasObjectTypesViewModel({Rectangle()}, AppCompatResources.getDrawable(requireContext(),R.drawable.rectangle_icon__1_), "Прямоугольник" ),
            CanvasObjectTypesViewModel({Oval()}, AppCompatResources.getDrawable(requireContext(),R.drawable.oval_icon__1_), "Овал" ),
        ))
        adapterCanvasObjectTypes.itemOnClick = {
            pos, type ->
            binding.canvasEditor.addObject(type.getCanvasObject.invoke())
            binding.canvasEditor.invalidate()
        }
        binding.CodeCanvasCreateLayout.setOnClickListener{
            binding.canvasEditor.clearSelected()
            binding.canvasEditor.invalidate()
        }
        binding.CanvasObjectIconsForChoiceRecyclerView.adapter = adapterCanvasObjectTypes
        binding.CanvasObjectIconsForChoiceRecyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
    }
}