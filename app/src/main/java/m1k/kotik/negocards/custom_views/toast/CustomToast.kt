package m1k.kotik.negocards.custom_views.toast

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import m1k.kotik.negocards.R
import m1k.kotik.negocards.databinding.CustomToastLayoutBinding
import m1k.kotik.negocards.databinding.FragmentQrcViewerBinding

fun Toast.showCustomToast(message: String,context: Context)
{
    val binding = CustomToastLayoutBinding.inflate(LayoutInflater.from(context))
    val layout = binding.root
    binding.textView10.text = message

    // use the application extension function
    this.apply {
        setGravity(Gravity.BOTTOM, 0, 250)
        duration = Toast.LENGTH_SHORT
        view = layout
        show()
    }
}