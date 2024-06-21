package m1k.kotik.negocards.fragments.pages.code_create_pages

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import m1k.kotik.negocards.R
import m1k.kotik.negocards.custom_views.toast.showCustomToast
import m1k.kotik.negocards.custom_views.windows.stylized_window.FloatingStylizedWindow
import m1k.kotik.negocards.data.ViewSaver
import m1k.kotik.negocards.data.canvas_qrc.canvas_serialization.CanvasSerialization
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.BitmapShape
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Oval
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.shapes.Rectangle
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.texts.CanvasText
import m1k.kotik.negocards.data.canvas_qrc.canvas_view.canvas_objects.texts.Phone
import m1k.kotik.negocards.data.code.CodeContentType
import m1k.kotik.negocards.data.code.CodeType
import m1k.kotik.negocards.data.code.code_generators.QRCGenerator
import m1k.kotik.negocards.data.date.SimpleDate
import m1k.kotik.negocards.data.db.QRCDB
import m1k.kotik.negocards.data.recycler_view_adapters.adapter_decorations.SpaceItemDecorator
import m1k.kotik.negocards.data.recycler_view_adapters.adapter_decorations.SpaceItemDecoratorHorizontal
import m1k.kotik.negocards.data.recycler_view_adapters.canvas_object_types.CanvasObjectTypesAdapter
import m1k.kotik.negocards.data.recycler_view_adapters.canvas_object_types.CanvasObjectTypesViewModel
import m1k.kotik.negocards.data.recycler_view_adapters.types_canvas_object_type.TypesCanvasObjectTypeAdapter
import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.databinding.FragmentCanvasCodeCreateBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.max
import kotlin.math.min


class CanvasCodeCreateFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var binding: FragmentCanvasCodeCreateBinding
    private lateinit var codeViewWindow: FloatingStylizedWindow
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCanvasCodeCreateBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = binding.root.findNavController()

        binding.CodeCanvasCreateLayout.setOnClickListener{
            binding.canvasEditor.clearSelected()
            binding.canvasEditor.invalidate()
        }

        val adapterCTypes = TypesCanvasObjectTypeAdapter(requireContext(), listOf(
            "Фигуры", "Текстовая информация"
        ))
        adapterCTypes.itemOnClick = {
                pos, type ->
            when(type){
                "Фигуры" ->{
                    binding.CanvasObjectIconsForChoiceRecyclerView.adapter = CanvasObjectTypesAdapter(requireContext(), listOf(
                        CanvasObjectTypesViewModel({Rectangle()}, AppCompatResources.getDrawable(requireContext(),R.drawable.rectangle_icon__1_), "Прямоугольник" ),
                        CanvasObjectTypesViewModel({Oval()}, AppCompatResources.getDrawable(requireContext(),R.drawable.oval_icon__1_), "Овал" ),
                    )).also {
                        it.itemOnClick = {
                                pos, type ->
                            binding.canvasEditor.addObject(type.getCanvasObject.invoke())
                            binding.canvasEditor.invalidate()
                        }
                    }
                }
                "Текстовая информация" ->{
                    binding.CanvasObjectIconsForChoiceRecyclerView.adapter = CanvasObjectTypesAdapter(requireContext(), listOf(
                        CanvasObjectTypesViewModel({CanvasText()}, AppCompatResources.getDrawable(requireContext(),R.drawable.text_lines_icon), "Tекст" ),
                        CanvasObjectTypesViewModel({Phone()}, AppCompatResources.getDrawable(requireContext(),R.drawable.phone_icon), "Телефон" )
                    )).also {
                        it.itemOnClick = {
                                pos, type ->
                            binding.canvasEditor.addObject(type.getCanvasObject.invoke())
                            binding.canvasEditor.invalidate()
                        }
                    }

                }
            }
        }
        binding.backArrow.setOnClickListener{
            binding.canvasEditor.backAction()
        }
        binding.recyclerView.adapter = adapterCTypes
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.addItemDecoration(SpaceItemDecoratorHorizontal(30))

        binding.shareCanvas.setOnClickListener {
            val codeImage = ViewSaver.getBitmapFromView(binding.canvasEditor)
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
                    startActivity(Intent.createChooser(shareIntent, "Поделиться изображением холста"))
                }
            }
        }

        binding.saveCanvasToDb.setOnClickListener {
            val codeBitmapImage = ViewSaver.getBitmapFromView(binding.canvasEditor)
            ViewSaver.saveBitmapInGallery(codeBitmapImage, requireContext())
            Toast(requireContext()).showCustomToast(
                "Изображение холста сохранено в галерею",
                requireContext()
            )
//            val window = FloatingStylizedWindow(requireContext(), R.layout.name_input)
//            window.also {
//                it.header = "Сохранение холста"
//                it.rootView.background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_square_15, null)
//                it.rootView.background.setTintList(ColorStateList.valueOf(0xEE181818.toInt()))
//                val name = it.contentView.findViewById<EditText>(R.id.editTextInNameInput).text.toString()
//                it.contentView.findViewById<Button>(R.id.continue_in_input_name).setOnClickListener{
//                    QRCDB(requireContext()).addCanvas(QRCDB.SavedCanvas(name, binding.canvasEditor.serialize().toString()))
//                    window.close()
//                    Toast(requireContext()).showCustomToast("Сохранено в базу", requireContext())
//                }
//
//            }
//            val height = requireContext().resources.displayMetrics.heightPixels
//            val width = requireContext().resources.displayMetrics.widthPixels
//            window.show(0,0, width, height, Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL)

        }
        binding.CanvasObjectIconsForChoiceRecyclerView.layoutManager = GridLayoutManager(requireContext(), 4)


//        codeViewWindow = FloatingStylizedWindow(requireContext(), R.layout.qrc_viewer_popup)
//        codeViewWindow.windowContentViewGroup.also {
//            it.background =
//                ResourcesCompat.getDrawable(resources, R.drawable.rounded_square_15, null)
//            it.backgroundTintList = ColorStateList.valueOf(0xF5252525.toInt())
//        }
//        codeViewWindow.header = "Просмотр кода"
//        codeViewWindow.isInstanceMustRecreated = false

        binding.qrcViewBtn.setOnClickListener {

            val bundleForCodeEditor = Bundle()
            bundleForCodeEditor.putInt("contentType", CodeContentType.Canvas.ordinal)
            bundleForCodeEditor.putString("value", binding.canvasEditor.serialize() )
            bundleForCodeEditor.putString("date", SimpleDate.getCurrentDate().toString())
            bundleForCodeEditor.putInt("codeType", CodeType.QRC.ordinal)
            navController.navigate(R.id.codeEditorFragment, bundleForCodeEditor)
//            val height = requireContext().resources.displayMetrics.heightPixels
//            val width = requireContext().resources.displayMetrics.widthPixels
//            val serObjects = binding.canvasEditor.serialize()!!
//            var qrcImage = QRCGenerator().generateCodeBitmap(serObjects)
//            codeViewWindow.contentView.findViewById<ImageView>(R.id.QRCImage).setImageBitmap(qrcImage)
//            codeViewWindow.show(0,0, min(height,width),min(height,width),
//                Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL)
        }

    }
}