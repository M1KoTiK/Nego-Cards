package m1k.kotik.negocards.data.recycler_view_adapters.canvas_object_types

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.code.ScannedCode
import m1k.kotik.negocards.data.code.ScannedCodeWithId
import m1k.kotik.negocards.data.recycler_view_adapters.TextIconViewModel
import m1k.kotik.negocards.databinding.IconWithTextItemBinding
import m1k.kotik.negocards.databinding.ScannedQrcItemBinding

class CanvasObjectTypesAdapter(val context: Context, private val typeList: List<CanvasObjectTypesViewModel>):
    RecyclerView.Adapter<CanvasObjectTypesAdapter.CanvasObjectTypesViewHolder>() {

    class CanvasObjectTypesViewHolder(scannedQrcItemBinding:  IconWithTextItemBinding)
        :RecyclerView.ViewHolder(scannedQrcItemBinding.root) {
        private val binding = scannedQrcItemBinding
        fun bind(canvasObjectType: CanvasObjectTypesViewModel, itemClickListener:(Int, CanvasObjectTypesViewModel)->Unit, typeItem: CanvasObjectTypesViewModel){
            binding.root.setOnClickListener {
                itemClickListener.invoke(bindingAdapterPosition, typeItem)
            }
            binding.imageView13.setImageDrawable(canvasObjectType.icon)
            binding.textView24.text = canvasObjectType.name
        }
    }
    var itemOnClick: (Int, CanvasObjectTypesViewModel) -> Unit = {_,_->}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CanvasObjectTypesViewHolder  {
        val binding = IconWithTextItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return CanvasObjectTypesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CanvasObjectTypesViewHolder, position: Int) {
        val typeItem = typeList[position]
        holder.bind(typeItem,itemOnClick, typeItem)
    }

    override fun getItemCount(): Int {
        return typeList.size
    }

}