package com.example.negocards.model.QR

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder

class QRCreator {
    fun getQRCToBitmap(data: String):Bitmap{
        val qrgEncoder = QRGEncoder(data, null, QRGContents.Type.TEXT, 1000)
        qrgEncoder.colorBlack = Color.BLACK
        qrgEncoder.colorWhite = Color.WHITE
        return qrgEncoder.getBitmap(0)
    }
}