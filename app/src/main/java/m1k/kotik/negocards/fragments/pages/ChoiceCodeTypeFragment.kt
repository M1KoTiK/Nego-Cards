package m1k.kotik.negocards.fragments.pages

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.recycler_view_adapters.CodeTypeForChoice.ChoiceTypeCodeAdapter
import m1k.kotik.negocards.data.recycler_view_adapters.CodeTypeForChoice.CodeTypeItemViewModel
import m1k.kotik.negocards.data.recycler_view_adapters.SpaceItemDecorator
import m1k.kotik.negocards.databinding.ChoiceQrcTypeForCreateBinding
import m1k.kotik.negocards.databinding.CodeForTypeChoiceItemBinding

class ChoiceCodeTypeFragment: Fragment() {
    lateinit var navController: NavController
    lateinit var binding: ChoiceQrcTypeForCreateBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChoiceQrcTypeForCreateBinding.inflate(inflater, container, false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = binding.root.findNavController()
        val codeTypes: MutableList<CodeTypeItemViewModel> = mutableListOf()
        codeTypes.add(
            CodeTypeItemViewModel(
                "Текст",
                "sdfsd",
                ResourcesCompat.getDrawable(resources, R.drawable.text_code_type, null)!!
            )
        )
        codeTypes.add(
            CodeTypeItemViewModel(
                "Холст",
                "sdfsd",
                ResourcesCompat.getDrawable(resources, R.drawable.canvas_code_type, null)!!
            )
        )
        codeTypes.add(
            CodeTypeItemViewModel(
                "Файл",
                "sdfsd",
                ResourcesCompat.getDrawable(resources, R.drawable.file_code_type, null)!!
            )
        )

        binding.ListCodeTypeForCreate.adapter =
            ChoiceTypeCodeAdapter(requireActivity(), codeTypes).also {
                it.itemOnClick = { sender ->
                    if(sender.name == "Холст"){
                        navController.navigate(R.id.canvasCodePreCreateFragment)
                    }
                }


                binding.ListCodeTypeForCreate.layoutManager = LinearLayoutManager(requireActivity())
                binding.ListCodeTypeForCreate.addItemDecoration(SpaceItemDecorator(30))

            }
    }
}