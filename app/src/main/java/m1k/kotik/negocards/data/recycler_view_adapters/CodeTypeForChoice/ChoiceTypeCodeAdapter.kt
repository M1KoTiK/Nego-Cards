package m1k.kotik.negocards.data.recycler_view_adapters.CodeTypeForChoice

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import m1k.kotik.negocards.data.qrc.QRCViewModel
import m1k.kotik.negocards.databinding.CodeForTypeChoiceItemBinding
import m1k.kotik.negocards.databinding.ScannedQrcItemBinding
import m1k.kotik.negocards.data.recycler_view_adapters.CodeTypeForChoice.CodeTypeItemViewModel

class ChoiceTypeCodeAdapter(val context: Context, private val CodeTypeViewModelList: List<CodeTypeItemViewModel>):
    RecyclerView.Adapter<ChoiceTypeCodeAdapter.ChoiceTypeCodeViewHolder>() {

    class ChoiceTypeCodeViewHolder(CodeTypeItemBinding: CodeForTypeChoiceItemBinding)
        : RecyclerView.ViewHolder(CodeTypeItemBinding.root) {
        private val binding = CodeTypeItemBinding
        private val defaultHeight = binding.root.layoutParams.height
        fun bind(codeTypeItemViewModel: CodeTypeItemViewModel, itemClickListener: (CodeTypeItemViewModel) -> Unit) {
            binding.root.setOnClickListener { itemClickListener.invoke(codeTypeItemViewModel) }
            binding.NameCodeForChoiceType.text = codeTypeItemViewModel.name
            binding.DescriptionCodeForChoiceType.text = codeTypeItemViewModel.desc
            binding.ImageCodeForChoiceType.setImageDrawable(codeTypeItemViewModel.image)
        }
    }
    var itemOnClick: (CodeTypeItemViewModel) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceTypeCodeViewHolder  {
        val binding = CodeForTypeChoiceItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ChoiceTypeCodeViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ChoiceTypeCodeViewHolder, position: Int) {
        val codeTypeItemBinding = CodeTypeViewModelList[position]
        holder.bind(codeTypeItemBinding,itemOnClick)
    }

    override fun getItemCount(): Int {
        return CodeTypeViewModelList.size
    }


}