package m1k.kotik.negocards.fragments.pages
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import m1k.kotik.negocards.CameraXViewModel
import m1k.kotik.negocards.data.date.SimpleDate
import m1k.kotik.negocards.data.canvas_qrc.old_govno.popup_windows.CanvasViewerPopupWindow
import m1k.kotik.negocards.data.code.CodeContentType
import m1k.kotik.negocards.data.code.ScannedCode
import m1k.kotik.negocards.databinding.FragmentScannerPageBinding
import m1k.kotik.negocards.data.db.QRCDB
import java.util.*
import java.util.concurrent.Executors
import m1k.kotik.negocards.R
import m1k.kotik.negocards.custom_views.toast.showCustomToast
import m1k.kotik.negocards.data.code.CodeType
import m1k.kotik.negocards.data.code.barcodeFormatToCodeType
import m1k.kotik.negocards.data.recycler_view_adapters.scanned_qrc.ScannedQrcAdapter


class ScannerPageFragment : Fragment() {
    private var binding: FragmentScannerPageBinding? = null
    private lateinit var navController: NavController
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

    private lateinit var db : QRCDB
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = binding?.root?.findNavController()!!
        db = QRCDB(requireContext())
        setupCamera()
        binding?.flashBtn?.imageTintList = ColorStateList.valueOf(0xFF909090.toInt())
        binding?.flashBtn?.setOnClickListener {
            setTorch()
        }
//        Для добавления тестовых данных

//        db.addScannedCode(
//            ScannedCode(
//                CodeType.QRC,
//                CodeContentType.Text,
//                "testData",
//                SimpleDate.getCurrentDate())
//        )

        refreshScannedQRC()
    }

    fun refreshScannedQRC(){
        val listScannedQRC = db.getScannedQRC().reversed()
        val adapter = ScannedQrcAdapter(requireActivity(), listScannedQRC ).also{ scannedQrcAdapter ->
            scannedQrcAdapter.itemOnClick = {
                val bundle = Bundle()
                val scannedQRC = listScannedQRC[it]
                bundle.putInt("id", scannedQRC.contentType.ordinal)
                bundle.putInt("contentType", scannedQRC.contentType.ordinal)
                bundle.putString("value", scannedQRC.value )
                bundle.putString("date", scannedQRC.date.toString())
                bundle.putInt("codeType", scannedQRC.codeType.ordinal)
                if(!scannedQRC.isOpened){
                    scannedQRC.isOpened = true
                    db.updateScannedQRCInDB(scannedQRC)
                }
                navController.navigate(R.id.QRCViewerFragment, bundle)
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
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    PERMISSION_CAMERA_REQUEST
                )
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CAMERA_REQUEST) {
            if (isCameraPermissionGranted()) {
                setupCamera()
            }
            else {
                Log.e(TAG, "no camera permission")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
                    val valueType = barcode.valueType
                    // See API reference for complete list of supported types
                    val listScannedQRC = db.getScannedQRC()
                    when (valueType) {

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
                            val lastScannedQRC = listScannedQRC.lastOrNull()
                            if( lastScannedQRC == null || lastScannedQRC.value != rawValue) {

                                db.addScannedCode(
                                    ScannedCode(
                                        barcodeFormatToCodeType(barcode.format),
                                        CodeContentType.Reference,
                                        rawValue,
                                        SimpleDate.getCurrentDate()
                                    )
                                )
                                refreshScannedQRC()
                            }
                        }
                        else -> {
                            val lastScannedQRC = listScannedQRC.lastOrNull()
                                if(lastScannedQRC == null || lastScannedQRC.value != rawValue) {
                                    if(rawValue.startsWith("urlto:")){

                                    }
                                    else if (rawValue.startsWith("canvas:")){
                                        db.addScannedCode(
                                            ScannedCode(
                                                barcodeFormatToCodeType(barcode.format),
                                                CodeContentType.Canvas,
                                                rawValue,
                                                SimpleDate.getCurrentDate())
                                        )
                                    }
                                    else {
                                        db.addScannedCode(
                                            ScannedCode(
                                                barcodeFormatToCodeType(barcode.format),
                                                CodeContentType.Text,
                                                rawValue,
                                                SimpleDate.getCurrentDate())
                                        )

                                    }
                                    refreshScannedQRC()
                                }

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
        else{
            Toast(requireContext()).showCustomToast("В вашем устройстве нет фонарика", requireContext())
        }
    }

    companion object {
        private val TAG = ScannerPageFragment::class.java.simpleName
        private const val PERMISSION_CAMERA_REQUEST = 1
    }
}