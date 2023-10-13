package m1k.kotik.negocards.fragments.pages
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import m1k.kotik.negocards.CameraXViewModel
import m1k.kotik.negocards.custom_models.date.SimpleDate
import m1k.kotik.negocards.data.canvas_qrc.model.popup_windows.CanvasViewerPopupWindow
import m1k.kotik.negocards.data.qrc.QRCType
import m1k.kotik.negocards.data.qrc.ScannedQRC
import m1k.kotik.negocards.data.qrc.ScannedQrcAdapter
import m1k.kotik.negocards.databinding.FragmentScannerPageBinding
import m1k.kotik.negocards.db.QRCDBHelper
import java.util.*
import java.util.concurrent.Executors


class ScannerPageFragment : Fragment() {
    private var binding: FragmentScannerPageBinding? = null
    private var previewView: PreviewView? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var cameraSelector: CameraSelector? = null
    private var lensFacing = CameraSelector.LENS_FACING_BACK
    private var previewUseCase: Preview? = null
    private var analysisUseCase: ImageAnalysis? = null
    var canvasViewer = CanvasViewerPopupWindow()
    private lateinit var cam: androidx.camera.core.Camera
    private var isLight = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScannerPageBinding.inflate(inflater, container, false)
        return binding!!.root

    }
    private lateinit var db : QRCDBHelper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = QRCDBHelper(requireContext())
        setupCamera()
        binding?.flashBtn?.imageTintList = ColorStateList.valueOf(0xFF909090.toInt())
        binding?.flashBtn?.setOnClickListener {
            setTorch()
        }

        val testList = db.getScannedQRC()
        val adapter = ScannedQrcAdapter(requireActivity(), testList).also{
            it.itemOnClick = {

            }
        }
        if (adapter.itemCount > 0) {
            binding?.placeholderTextRecView?.visibility = View.INVISIBLE
            binding?.listScannedQRC?.adapter = adapter
            binding?.listScannedQRC?.layoutManager = LinearLayoutManager(requireActivity())
        }
        else{
            binding?.placeholderTextRecView?.visibility = View.VISIBLE
        }

    }

    override fun onStop() {
        super.onStop()
    }

    val imageCapture = ImageCapture.Builder()
    .setFlashMode(ImageCapture.FLASH_MODE_ON)
    .build()

    private fun setupCamera() {
        previewView = binding?.previewView
        cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
        ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(CameraXViewModel::class.java)
            .processCameraProvider
            .observe(requireActivity()) { provider: ProcessCameraProvider? -> cameraProvider = provider
            if (isCameraPermissionGranted()) {
                bindCameraUseCases()
            }
            else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    PERMISSION_CAMERA_REQUEST)
            }
        }
    }

    private fun bindCameraUseCases() {
        bindPreviewUseCase()
        bindAnalyseUseCase()
    }

    private fun bindPreviewUseCase() {
        if (cameraProvider == null) {
            return
        }
        if (previewUseCase != null) {
            cameraProvider!!.unbind(previewUseCase)
        }
        previewUseCase = Preview.Builder()
            .setTargetRotation(previewView!!.display.rotation)
            .build()
        previewUseCase!!.setSurfaceProvider(previewView!!.surfaceProvider)
        try {
            cam = cameraProvider!!.bindToLifecycle(
                this,
                cameraSelector!!,
                previewUseCase,
                imageCapture
            )
        } catch (illegalStateException: IllegalStateException) {
            Log.e(TAG, illegalStateException.message ?: "IllegalStateException")
        } catch (illegalArgumentException: IllegalArgumentException) {
            Log.e(TAG, illegalArgumentException.message ?: "IllegalArgumentException")
        }
    }

    private fun bindAnalyseUseCase() {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).build()

        val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient(options)

        if (cameraProvider == null) {
            return
        }
        if (analysisUseCase != null) {
            cameraProvider!!.unbind(analysisUseCase)
        }

        analysisUseCase = ImageAnalysis.Builder()
            .setTargetRotation(previewView!!.display.rotation)
            .build()

        // Initialize our background executor
        val cameraExecutor = Executors.newSingleThreadExecutor()

        analysisUseCase?.setAnalyzer(
            cameraExecutor,
            ImageAnalysis.Analyzer { imageProxy ->
                processImageProxy(barcodeScanner, imageProxy)
            }
        )

        try {
            cameraProvider!!.bindToLifecycle(
                this,
                cameraSelector!!,
                analysisUseCase
            )
        } catch (illegalStateException: IllegalStateException) {
            Log.e(TAG, illegalStateException.message ?: "IllegalStateException")
        } catch (illegalArgumentException: IllegalArgumentException) {
            Log.e(TAG, illegalArgumentException.message ?: "IllegalArgumentException")
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun processImageProxy(
        barcodeScanner: BarcodeScanner,
        imageProxy: ImageProxy
    ) {
        val inputImage =
            InputImage.fromMediaImage(imageProxy.image!!, imageProxy.imageInfo.rotationDegrees)

        barcodeScanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                barcodes.forEach { barcode ->
                    val bounds = barcode.boundingBox
                    val corners = barcode.cornerPoints

                    val rawValue = barcode.rawValue

//                    canvasViewer.setup(requireActivity(),700,700)
//                    canvasViewer.canvas.getObjectsFromCode("""ot\"tx\"w169h92x165y319s\"sf""")
//                    canvasViewer.show(0,0,Gravity.CENTER)

                    val valueType = barcode.valueType
                    // See API reference for complete list of supported types

                    when (valueType) {
                        /*
                        Barcode.TYPE_WIFI -> {
                            val ssid = barcode.wifi!!.ssid
                            val password = barcode.wifi!!.password
                            val type = barcode.wifi!!.encryptionType
                           // binding?.tvScannedData?.text =
                           //     "ssid: " + ssid + "\npassword: " + password + "\ntype: " + type
                        }
                        Barcode.TYPE_URL -> {
                          val title = barcode.url!!.title
                            val url = barcode.url!!.url
                            //binding?.tvScannedData?.text = "Title: " + title + "\nURL: " + url
                        }
                        */
                        else -> {
                            db.add(ScannedQRC(QRCType.Text, rawValue, SimpleDate.Companion.getCurrentDate()))
                        }
                    }

                }
            }
            .addOnFailureListener {
                Log.e(TAG, it.message ?: it.toString())
            }
            .addOnCompleteListener {
                // When the image is from CameraX analysis use case, must call image.close() on received
                // images when finished using them. Otherwise, new images may not be received or the camera
                // may stall.
                imageProxy.close()

            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CAMERA_REQUEST) {
            if (isCameraPermissionGranted()) {
                // start camera
            }
            else {
                Log.e(TAG, "no camera permission")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

//        cameraManager = activity?.getSystemService(CAMERA_SERVICE) as CameraManager
//        try {
//            for(cameraID in cameraManager.cameraIdList) {
//                val characteristics = cameraManager.getCameraCharacteristics(cameraID)
//                val isHasFlashUnit = characteristics.get(FLASH_INFO_AVAILABLE)
//                if (isHasFlashUnit!!){
//                    cameraManager.setTorchMode(cameraID, true)
//                }
//            }
//        }
//        catch (e:Exception){
//
//        }
    private fun setTorch(){
        if (cam.cameraInfo.hasFlashUnit() ) {
            if(isLight) {
                binding?.flashBtn?.imageTintList = ColorStateList.valueOf(0xFF909090.toInt())
                cam.cameraControl.enableTorch(false)
                isLight = false
            }
            else {
                binding?.flashBtn?.imageTintList = ColorStateList.valueOf(0xFF000000.toInt())
                cam.cameraControl.enableTorch(true)
                isLight = true
            }
        }
    }

    companion object {
        private val TAG = ScannerPageFragment::class.java.simpleName
        private const val PERMISSION_CAMERA_REQUEST = 1
    }
}