package m1k.kotik.negocards.fragments.pages

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import m1k.kotik.negocards.R
import m1k.kotik.negocards.data.custom_views.toast.showCustomToast
import m1k.kotik.negocards.data.date.SimpleDate
import m1k.kotik.negocards.data.qrc.QRCType
import m1k.kotik.negocards.data.qrc.QRCViewModel
import m1k.kotik.negocards.data.qrc.QRCreator
import m1k.kotik.negocards.databinding.FragmentQrcViewerBinding

class QRCViewerFragment(): Fragment() {
    private lateinit var binding: FragmentQrcViewerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentQrcViewerBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle: Bundle = this.requireArguments()
        val type: QRCType? = QRCType.values().firstOrNull{ it.ordinal == bundle.getInt("type")}
        val value: String? = bundle.getString("value")
        val date: SimpleDate? = SimpleDate.toSimpleDate(bundle.getString("date")?: "")
        var QRCViewModel:QRCViewModel
        if(type!=null && value!=null && date != null){
            QRCViewModel = QRCViewModel(type,value,date)
            binding.QRCValueViewer.setText(QRCViewModel.value)
            binding.qrcDisplay.setImageBitmap(QRCreator.getQRCToBitmap(value))
            binding.copyBtn.setOnClickListener {
                val clipboardManager = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText(
                    "nonsense_data",
                    value
                )
                clipboardManager.setPrimaryClip(clipData)
                Toast(requireContext()).showCustomToast("Скопировано!",requireActivity())
            }

        }

    }
}