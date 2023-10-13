package m1k.kotik.negocards.fragments.pages

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import m1k.kotik.negocards.data.date.SimpleDate
import m1k.kotik.negocards.data.qrc.QRCType
import m1k.kotik.negocards.data.qrc.ScannedQRC
import m1k.kotik.negocards.databinding.FragmentQrcViewPageBinding
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
        var scannedQRC:ScannedQRC
        if(type!=null && value!=null && date != null){
            scannedQRC = ScannedQRC(type,value,date)
            binding.QRCValueViewer.setText(scannedQRC.value)
        }

    }
}