package m1k.kotik.negocards.data.recycler_view_adapters.text_object_in_canvas

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import m1k.kotik.negocards.custom_views.toast.showCustomToast
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.texts.ICanvasText
import m1k.kotik.negocards.data.recycler_view_adapters.canvas_object_types.CanvasObjectTypesAdapter
import m1k.kotik.negocards.data.recycler_view_adapters.canvas_object_types.CanvasObjectTypesViewModel
import m1k.kotik.negocards.databinding.IconWithTextItemBinding
import m1k.kotik.negocards.databinding.TextObjInCanvasItemBinding

class TextObjectInCanvasAdapter(val context: Context, private val typeList: List<Any>):
    RecyclerView.Adapter<TextObjectInCanvasAdapter.TextObjectInCanvasViewHolder>() {

    class TextObjectInCanvasViewHolder(textObjInCanvasItemBinding: TextObjInCanvasItemBinding) :
        RecyclerView.ViewHolder(textObjInCanvasItemBinding.root) {
        private val binding = textObjInCanvasItemBinding
        fun bind(
            context: Context,
            typeItem: ICanvasText,
            itemClickListener: (Int, ICanvasText) -> Unit
        ) {
            binding.copyInTextObjectCanvas.setOnClickListener {
                val clipboardManager =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText(
                    "nonsense_data",
                    binding.textInTextObj.text.toString()
                )
                clipboardManager.setPrimaryClip(clipData)
                Toast(context).showCustomToast("Скопировано!", context)
            }
            binding.root.setOnClickListener {
                itemClickListener.invoke(bindingAdapterPosition, typeItem)
            }

        }
    }

    var itemOnClick: (Int, ICanvasText) -> Unit = { _, _ -> }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextObjectInCanvasViewHolder {
        val binding = TextObjInCanvasItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return TextObjectInCanvasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextObjectInCanvasViewHolder, position: Int) {
        val typeItem = typeList[position]
        if(typeItem is ICanvasText) {
            holder.bind(context, typeItem, itemOnClick)
        }
    }

    override fun getItemCount(): Int {
        return typeList.size
    }
}