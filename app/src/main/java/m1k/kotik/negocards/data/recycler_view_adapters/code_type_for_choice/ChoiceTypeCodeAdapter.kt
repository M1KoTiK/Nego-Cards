package m1k.kotik.negocards.data.recycler_view_adapters.code_type_for_choice

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import m1k.kotik.negocards.databinding.CodeForTypeChoiceItemBinding

class ChoiceTypeCodeAdapter(val context: Context, private val CodeTypeViewModelList: List<CodeTypeItem>):
    RecyclerView.Adapter<ChoiceTypeCodeAdapter.ChoiceTypeCodeViewHolder>() {

    class ChoiceTypeCodeViewHolder(CodeTypeItemBinding: CodeForTypeChoiceItemBinding)
        : RecyclerView.ViewHolder(CodeTypeItemBinding.root) {
        private val binding = CodeTypeItemBinding
        private val defaultHeight = binding.root.layoutParams.height
        fun bind(codeTypeItem: CodeTypeItem, itemClickListener: (CodeTypeItem) -> Unit) {
            binding.root.setOnClickListener { itemClickListener.invoke(codeTypeItem) }
            binding.NameCodeForChoiceType.text = codeTypeItem.name
            binding.DescriptionCodeForChoiceType.text = codeTypeItem.desc
            binding.ImageCodeForChoiceType.setImageDrawable(codeTypeItem.image)
        }
    }
    var itemOnClick: (CodeTypeItem) -> Unit = {}
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