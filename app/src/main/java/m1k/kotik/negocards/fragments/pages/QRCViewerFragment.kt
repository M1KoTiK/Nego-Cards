package m1k.kotik.negocards.fragments.pages

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainLayoutQRCViewer.setOnTouchListener(OnTouchListener { v, event ->
            if (binding.QRCValueViewer.isFocused) {
                if (event.y > binding.QRCValueViewer.y - binding.QRCValueViewer.height ||
                    event.y < binding.QRCValueViewer.y + binding.QRCValueViewer.height ||
                    event.x > binding.QRCValueViewer.x + binding.QRCValueViewer.width ||
                    event.y < binding.QRCValueViewer.x - binding.QRCValueViewer.width) {
                    binding.QRCValueViewer.clearFocus()
                }
            }
            false
        })
        val bundle: Bundle = this.requireArguments()
        val type: QRCType? = QRCType.values().firstOrNull{ it.ordinal == bundle.getInt("type")}
        val value: String? = bundle.getString("value")
        val date: SimpleDate? = SimpleDate.toSimpleDate(bundle.getString("date")?: "")
        var qrcViewModel:QRCViewModel
        if(type!=null && value!=null && date != null){
            qrcViewModel = QRCViewModel(type,value,date)
            binding.QRCValueViewer.text = qrcViewModel.value
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
        binding.openInBrowserBtn.setOnClickListener {
            val url = ""
            var browserIntent: Intent
            browserIntent = Intent(Intent.ACTION_VIEW)
            if (binding.QRCValueViewer.text.startsWith("http://") && binding.QRCValueViewer.text.startsWith("https://")) {
                browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            }
            startActivity(browserIntent)
        }

    }
}