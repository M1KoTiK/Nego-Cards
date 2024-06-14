package m1k.kotik.negocards.data.recycler_view_adapters.types_canvas_object_type

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import m1k.kotik.negocards.data.recycler_view_adapters.canvas_object_types.CanvasObjectTypesAdapter
import m1k.kotik.negocards.data.recycler_view_adapters.canvas_object_types.CanvasObjectTypesViewModel
import m1k.kotik.negocards.databinding.DotedItemBinding
import m1k.kotik.negocards.databinding.IconWithTextItemBinding

class TypesCanvasObjectTypeAdapter(val context: Context, private val typeList: List<String>):
    RecyclerView.Adapter<TypesCanvasObjectTypeAdapter.TypesCanvasObjectTypeViewHolder>() {

    class TypesCanvasObjectTypeViewHolder(dotedItemBinding: DotedItemBinding)
        : RecyclerView.ViewHolder(dotedItemBinding.root) {
        private val binding = dotedItemBinding
        fun bind(itemClickListener:(Int, String)->Unit, typeItem: String){
            binding.root.setOnClickListener {
                itemClickListener.invoke(bindingAdapterPosition, typeItem)
            }
            binding.nameCType.text = typeItem
        }
    }
    var itemOnClick: (Int, String) -> Unit = { _, _->}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypesCanvasObjectTypeViewHolder  {
        val binding = DotedItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return TypesCanvasObjectTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TypesCanvasObjectTypeViewHolder, position: Int) {
        val typeItem = typeList[position]
        holder.bind(itemOnClick, typeItem)
    }

    override fun getItemCount(): Int {
        return typeList.size
    }

}