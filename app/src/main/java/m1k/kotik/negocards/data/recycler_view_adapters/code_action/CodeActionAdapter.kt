package m1k.kotik.negocards.data.recycler_view_adapters.code_action

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import m1k.kotik.negocards.data.code.ScannedCode
import m1k.kotik.negocards.databinding.CodeActionItemBinding

class CodeActionAdapter (val context: Context, private val scannedCode: ScannedCode):
        RecyclerView.Adapter<CodeActionAdapter.CodeActionViewHolder>() {

        class CodeActionViewHolder(codeActionItemBinding: CodeActionItemBinding)
            : RecyclerView.ViewHolder(codeActionItemBinding.root) {

            private val binding = codeActionItemBinding
            fun bind(context: Context, scannedCode: ScannedCode, position: Int){
                val codeAction = scannedCode.contentType.actionList[position]
                binding.CodeActionTitle.text = codeAction.nameAction
                binding.root.setOnClickListener {
                    codeAction.action.invoke(context, scannedCode)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodeActionViewHolder  {
            val binding = CodeActionItemBinding.inflate(LayoutInflater.from(context),parent,false)
            return CodeActionViewHolder(binding)
        }

        override fun onBindViewHolder(holder: CodeActionViewHolder, position: Int) {
            holder.bind(context, scannedCode, position)
        }

        override fun getItemCount(): Int {
            return scannedCode.contentType.actionList.size
        }



}