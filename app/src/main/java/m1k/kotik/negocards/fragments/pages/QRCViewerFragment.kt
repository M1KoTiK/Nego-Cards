package m1k.kotik.negocards.fragments.pages

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import m1k.kotik.negocards.R
import m1k.kotik.negocards.custom_views.layouts.BackPressNotifyingLinearLayout
import m1k.kotik.negocards.custom_views.toast.showCustomToast
import m1k.kotik.negocards.custom_views.windows.StaticWindow
import m1k.kotik.negocards.custom_views.windows.stylized_window.FloatingStylizedWindow
import m1k.kotik.negocards.data.code.CodeContentType
import m1k.kotik.negocards.data.code.CodeType
import m1k.kotik.negocards.data.code.ScannedCode
import m1k.kotik.negocards.data.code.code_generators.QRCGenerator
import m1k.kotik.negocards.data.date.SimpleDate
import m1k.kotik.negocards.data.recycler_view_adapters.adapter_decorations.SpaceItemDecorator
import m1k.kotik.negocards.data.recycler_view_adapters.code_action.CodeActionAdapter
import m1k.kotik.negocards.databinding.FragmentQrcViewerBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


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
    private lateinit var codeViewWindow: FloatingStylizedWindow
    private lateinit var menuWindow: StaticWindow

    //Кривой костыль для отмены фокуса
    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        codeViewWindow = FloatingStylizedWindow(requireContext(), R.layout.qrc_viewer_popup)
        codeViewWindow.windowContentViewGroup.also {
            it.background =
                ResourcesCompat.getDrawable(resources, R.drawable.rounded_square_15, null)
            it.backgroundTintList = ColorStateList.valueOf(0xF5252525.toInt())
        }
        codeViewWindow.header = "Просмотр кода"
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
        val contentType: CodeContentType? = CodeContentType.values().firstOrNull { it.ordinal == bundle.getInt("contentType") }
        val value: String? = bundle.getString("value")
        val date: SimpleDate? = SimpleDate.toSimpleDate(bundle.getString("date") ?: "")
        val codeType: CodeType? = CodeType.values().firstOrNull { it.ordinal == bundle.getInt("codeType") }
        var codeImage: Bitmap? = null
        var scannedCode: ScannedCode
        if (contentType != null && value != null && date != null && codeType != null) {
            scannedCode = ScannedCode(codeType,contentType, value, date)
            val codeImageView = codeViewWindow.contentView.findViewById<ImageView>(R.id.QRCImage)
            binding.QRCValueViewer.text = scannedCode.value
            codeImage = QRCGenerator.getQRCToBitmap(value)
            binding.qrcDisplay.setImageBitmap(codeImage)
            binding.qrcViewerDateQRC.text = date.toString()
            binding.QRCViewerCodeType.text = codeType.typeName
            binding.codeActionsRecyclerView.adapter = CodeActionAdapter(requireActivity(), scannedCode)
            binding.codeActionsRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            binding.codeActionsRecyclerView.addItemDecoration(SpaceItemDecorator(10))
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

        menuWindow = StaticWindow(requireContext(), R.layout.menu_for_qrc_viewer, R.layout.empty_layout).also {
            it.windowParameters = WindowManager.LayoutParams(
                0,
                0,
                0,
                0,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    WindowManager.LayoutParams.TYPE_APPLICATION
                } else {
                    WindowManager.LayoutParams.TYPE_APPLICATION
                },
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
            )
            it.rootView.setOnTouchListener { v , event ->
                if (event.y > v.y - v.height ||
                    event.y < v.y + v.height ||
                    event.x > v.x + v.width ||
                    event.y < v.x - v.width
                ) {
                    it.close()
                }
                false
            }
        }
        menuWindow.contentView.findViewById<LinearLayout>(R.id.share_content).setOnClickListener {
            val intent= Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,value)
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Поделиться текстом кода"))
        }
        menuWindow.contentView.findViewById<LinearLayout>(R.id.share_image).setOnClickListener {
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
                    startActivity(Intent.createChooser(shareIntent, "Choose an app"))
                }
            }
        }
        (menuWindow.rootView as BackPressNotifyingLinearLayout).onBackPressed = {menuWindow.close()}
        binding.menuButtonCodeViewer.setOnClickListener {
            menuWindow.show(100,275,550,550,
                Gravity.TOP or Gravity.RIGHT)
        }


        binding.qrcDisplay.setOnClickListener {
            codeViewWindow.show(0,0,1000,1100,
                Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL)
        }
    }
}