package m1k.kotik.negocards.fragments.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        }

    }
}