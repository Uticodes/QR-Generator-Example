package com.example.qr_generator_example.usecases

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import com.example.qr_generator_example.utils.Resource
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException

class GenerateQRCodeUseCase constructor(private val codeWriter : MultiFormatWriter) {

fun generateQRCode(url: String, bitmap: Bitmap): Resource<Bitmap>{

    try {
        if (url.isNotEmpty()){
            val bitMatrix = codeWriter.encode(url, BarcodeFormat.QR_CODE, width, height)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }else {
            return Resource(status = Resource.Status.ERROR, data = bitmap, message = "QR code ur should not be empty")
        }

    } catch (e: WriterException) {
        Log.d("GenerateQRCode", "generateQRCode: ${e.message}")
        return Resource(status = Resource.Status.ERROR, data = bitmap, message = e.message)
    }
    return Resource(status = Resource.Status.SUCCESS, data = bitmap, message = "QR code generate successfully")

}

    companion object{
        const  val width = 500
        const val height = 500
    }
}