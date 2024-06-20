package m1k.kotik.negocards.fragments.pages

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import m1k.kotik.negocards.R
import m1k.kotik.negocards.custom_views.toast.showCustomToast
import m1k.kotik.negocards.data.code.CodeContentType
import m1k.kotik.negocards.data.code.CodeType
import m1k.kotik.negocards.data.code.ScannedCode
import m1k.kotik.negocards.data.code.code_generators.QRCGenerator
import m1k.kotik.negocards.data.date.SimpleDate
import m1k.kotik.negocards.data.recycler_view_adapters.adapter_decorations.SpaceItemDecorator
import m1k.kotik.negocards.data.recycler_view_adapters.code_action.CodeActionAdapter
import m1k.kotik.negocards.databinding.DotedItemBinding
import m1k.kotik.negocards.databinding.FragmentChoiceTypeQrcBinding
import m1k.kotik.negocards.databinding.FragmentCodeEditorBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class CodeEditorFragment : Fragment() {
    lateinit var binding: FragmentCodeEditorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCodeEditorBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle: Bundle = this.requireArguments()
        val contentType: CodeContentType? = CodeContentType.values().firstOrNull { it.ordinal == bundle.getInt("contentType") }
        val value: String? = bundle.getString("value")
        val codeType: CodeType? = CodeType.values().firstOrNull { it.ordinal == bundle.getInt("codeType") }
        var codeImage: Bitmap? = null
        var scannedCode: ScannedCode
        if (contentType != null && value != null && codeType != null) {

        }

        binding.shareCodeTextInCodeEditor.setOnClickListener {
            val intent= Intent()
            intent.action= Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,value)
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Поделиться текстом кода"))
        }
        binding.shareImageQrcInCodeEditor.setOnClickListener {
            if (codeImage != null) {
                try {
                    val cachePath = File(requireContext().cacheDir, "images")
                    cachePath.mkdirs() // don't forget to make the directory
                    val stream =
                        FileOutputStream(cachePath.toString() + "/image.png") // overwrites this image every time
                    codeImage.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    stream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                val imagePath = File(requireContext().cacheDir, "images")
                val newFile = File(imagePath, "image.png")
                val contentUri: Uri? =
                    FileProvider.getUriForFile(requireContext(), "m1k.kotik.negocards.fileprovider", newFile)

                if (contentUri != null) {
                    val shareIntent = Intent()
                    shareIntent.action = Intent.ACTION_SEND
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // temp permission for receiving app to read this file
                    shareIntent.setDataAndType(contentUri, requireContext().contentResolver.getType(contentUri))
                    shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
                    startActivity(Intent.createChooser(shareIntent, "Поделиться изображением кода"))
                }
            }
        }
    }



}