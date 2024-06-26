package m1k.kotik.negocards.data.recycler_view_adapters.scanned_qrc

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.code.ScannedCodeWithId
import m1k.kotik.negocards.databinding.ScannedQrcItemBinding

private fun setHeightForView(height: Int, view: View){
    val params = view.layoutParams
    params.height = height
    view.layoutParams = params
    view.requestLayout()
}
class ScannedQrcAdapter(val context: Context, private val scannedCodeList: List<ScannedCodeWithId>):
    RecyclerView.Adapter<ScannedQrcAdapter.ScannedQRCViewHolder>() {

        class ScannedQRCViewHolder(scannedQrcItemBinding: ScannedQrcItemBinding)
            :RecyclerView.ViewHolder(scannedQrcItemBinding.root) {
            private val binding = scannedQrcItemBinding
            private val defaultHeight  = binding.root.layoutParams.height
            fun bind(ScannedCode: ScannedCodeWithId, itemClickListener:(Int)->Unit){
                binding.root.setOnClickListener {
                    itemClickListener.invoke(bindingAdapterPosition)
                }
                    when (bindingAdapterPosition) {
                        0 -> {
                            //setHeightForView(200, binding.root)
                            binding.root.background.setTintList(ColorStateList.valueOf(0xFFD9D9D9.toInt()))

                        }
                        else -> {
                            binding.root.background.setTintList(ColorStateList.valueOf(0xFF909090.toInt()))
                            setHeightForView(defaultHeight, binding.root)
                        }
                    }
                if(ScannedCode.isOpened){
                    binding.root.findViewById<ImageView>(R.id.isOpenQRCImageView).setImageDrawable(null)
                }

                binding.QRCType.text = ScannedCode.contentType.typeName
                binding.QRCValue.text = ScannedCode.value
            }
        }
    var itemOnClick: (Int) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScannedQRCViewHolder  {
        val binding = ScannedQrcItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ScannedQRCViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScannedQRCViewHolder, position: Int) {
        val scannedQRCItem = scannedCodeList[position]
        holder.bind(scannedQRCItem,itemOnClick)
    }

    override fun getItemCount(): Int {
        return scannedCodeList.size
    }


}