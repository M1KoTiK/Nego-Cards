package m1k.kotik.negocards.data.qrc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import m1k.kotik.negocards.databinding.ScannedQrcItemBinding

class ScannedQrcAdapter(val context: Context, private val scannedQRCList: List<ScannedQRC>):
    RecyclerView.Adapter<ScannedQrcAdapter.ScannedQRCViewHolder>() {

        class ScannedQRCViewHolder(scannedQrcItemBinding: ScannedQrcItemBinding)
            :RecyclerView.ViewHolder(scannedQrcItemBinding.root) {
            private val binding = scannedQrcItemBinding
            fun bind(scannedQRC: ScannedQRC, itemClickListener:(Int)->Unit){
                binding.root.setOnClickListener { itemClickListener.invoke(bindingAdapterPosition) }
                binding.QRCType.text = scannedQRC.type.name
                binding.QRCValue.text = scannedQRC.value
            }
        }
    var itemOnClick: (Int) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScannedQRCViewHolder  {
        val binding = ScannedQrcItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ScannedQRCViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScannedQRCViewHolder, position: Int) {
        val scannedQRCItem = scannedQRCList[position]
        holder.bind(scannedQRCItem,itemOnClick)
    }

    override fun getItemCount(): Int {
        return scannedQRCList.size
    }


}