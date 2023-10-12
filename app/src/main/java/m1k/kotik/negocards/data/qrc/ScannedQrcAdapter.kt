package m1k.kotik.negocards.data.qrc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import m1k.kotik.negocards.R
import m1k.kotik.negocards.databinding.ScannedQrcItemBinding

class ScannedQrcAdapter(val context: Context, private val scannedQRCList: List<ScannedQRC>):
    RecyclerView.Adapter<ScannedQrcAdapter.ScannedQRCViewHolder>() {

        class ScannedQRCViewHolder(scannedQrcItemBinding: ScannedQrcItemBinding)
            :RecyclerView.ViewHolder(scannedQrcItemBinding.root) {
            private val binding = scannedQrcItemBinding

            fun bind(scannedQRC: ScannedQRC){
                binding.QRCType.text = scannedQRC.type.name
                binding.QRCValue.text = scannedQRC.value
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScannedQRCViewHolder {
        val binding = ScannedQrcItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ScannedQRCViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScannedQRCViewHolder, position: Int) {
        val foodItem = scannedQRCList[position]
        holder.bind(foodItem)
    }

    override fun getItemCount(): Int {
        return scannedQRCList.size
    }


}