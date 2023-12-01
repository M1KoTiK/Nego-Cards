package m1k.kotik.negocards.fragments.pages

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import m1k.kotik.negocards.R
import m1k.kotik.negocards.custom_views.toast.showCustomToast
import m1k.kotik.negocards.custom_views.windows.stylized_window.FloatingStylizedWindow
import m1k.kotik.negocards.data.date.SimpleDate
import m1k.kotik.negocards.data.qrc.CodeContentType
import m1k.kotik.negocards.data.qrc.QRCViewModel
import m1k.kotik.negocards.data.qrc.QRCreator
import m1k.kotik.negocards.databinding.FragmentQrcViewerBinding


class QRCViewerFragment() : Fragment() {
    private lateinit var binding: FragmentQrcViewerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQrcViewerBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    var codeViewWindow = FloatingStylizedWindow(requireContext(), R.layout.qrc_viewer_popup)

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainLayoutQRCViewer.setOnTouchListener(OnTouchListener { v, event ->
            if (binding.QRCValueViewer.isFocused) {
                if (event.y > binding.QRCValueViewer.y - binding.QRCValueViewer.height ||
                    event.y < binding.QRCValueViewer.y + binding.QRCValueViewer.height ||
                    event.x > binding.QRCValueViewer.x + binding.QRCValueViewer.width ||
                    event.y < binding.QRCValueViewer.x - binding.QRCValueViewer.width
                ) {
                    binding.QRCValueViewer.clearFocus()
                }
            }
            false
        })
        val bundle: Bundle = this.requireArguments()
        val type: CodeContentType? = CodeContentType.values().firstOrNull { it.ordinal == bundle.getInt("type") }
        val value: String? = bundle.getString("value")
        val date: SimpleDate? = SimpleDate.toSimpleDate(bundle.getString("date") ?: "")
        var qrcViewModel: QRCViewModel
        if (type != null && value != null && date != null) {
            qrcViewModel = QRCViewModel(type, value, date)
            val codeImageView = codeViewWindow.contentView.findViewById<ImageView>(R.id.QRCImage)
            binding.QRCValueViewer.text = qrcViewModel.value
            val codeImage = QRCreator.getQRCToBitmap(value)
            binding.qrcDisplay.setImageBitmap(codeImage)
            codeViewWindow.contentView.findViewById<ImageView>(R.id.QRCImage).setImageBitmap(codeImage)
            binding.copyBtn.setOnClickListener {
                val clipboardManager =
                    requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText(
                    "nonsense_data",
                    value
                )
                clipboardManager.setPrimaryClip(clipData)
                Toast(requireContext()).showCustomToast("Скопировано!", requireActivity())
            }

        }


        binding.qrcDisplay.setOnClickListener {
            codeViewWindow.show(0,0,1000,1100,
                Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL)
        }
        binding.openInBrowserBtn.setOnClickListener {
            val browserIntent = if (
                binding.QRCValueViewer.text.startsWith("http://") ||
                binding.QRCValueViewer.text.startsWith("https://")
            ) {
                Intent(Intent.ACTION_VIEW, Uri.parse(binding.QRCValueViewer.text.toString()))
            } else {
                Intent(Intent.ACTION_VIEW, Uri.parse("https://ya.ru/search/?text=" + binding.QRCValueViewer.text.toString()))
            }
            startActivity(browserIntent)
        }
    }
}