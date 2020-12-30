package com.example.qr_generator_example.viewModel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Environment
import android.util.Log
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidmads.library.qrgenearator.QRGSaver
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.qr_generator_example.utils.Resource
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class QRViewModel(application: Application) : AndroidViewModel(application){

    private val _generate = MutableLiveData<Resource<Bitmap>>()
    val generateStatus: LiveData<Resource<Bitmap>>
        get() = _generate

    fun generateQRCode(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val width = 500
            val height = 500
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val codeWriter = MultiFormatWriter()

            try {
                if (url.isNotEmpty()){
                    val bitMatrix = codeWriter.encode(url, BarcodeFormat.QR_CODE, width, height)
                    for (x in 0 until width) {
                        for (y in 0 until height) {
                            bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                        }
                    }
                }

            } catch (e: WriterException) {
                Log.d("GenerateQRCode", "generateQRCode: ${e.message}")
            }
            _generate.postValue(Resource(status = Resource.Status.SUCCESS, data = bitmap, message = "QR code generate successfully"))

        }
    }

    fun saveQRCode(url: String){
        viewModelScope.launch(Dispatchers.IO) {
            val qrgEncoder = QRGEncoder(url, null, QRGContents.Type.TEXT, 150)
            val bitmap = qrgEncoder.encodeAsBitmap()
        try {
            if (url.isNotEmpty()) {

                //qrImage.setImageBitmap(bitmap)
                var fileName = "QRCode_" + System.currentTimeMillis() + ".jpg"
                val file = File(Environment.getExternalStorageDirectory(), fileName)
                file.createNewFile()
                val saveLocation = file.parent + File.separator
                fileName = file.name.substring(0, file.name.indexOf("."))
                QRGSaver.save(saveLocation, fileName, bitmap, QRGContents.ImageType.IMAGE_JPEG)
                /*Toast.makeText(
                    this,
                    "QR Code successfully saved in the external storage!",
                    Toast.LENGTH_LONG
            ).show()*/
            }
        } catch (e: WriterException) {

            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
         _generate.postValue(Resource(status = Resource.Status.SUCCESS, data = bitmap, message = "QR code generate and saved successfully"))
         _generate.postValue(Resource(status = Resource.Status.ERROR, data = bitmap, message = "QR code failed to save"))
        }
    }

}