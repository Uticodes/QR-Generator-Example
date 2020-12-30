package com.example.qr_generator_example.viewModel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Environment
import android.util.Log
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidmads.library.qrgenearator.QRGSaver
import androidx.lifecycle.*
import com.example.qr_generator_example.usecases.GenerateQRCodeUseCase
import com.example.qr_generator_example.utils.Resource
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class QRViewModel constructor(private val useCase: GenerateQRCodeUseCase) : ViewModel(){

    private val _generate = MutableLiveData<Resource<Bitmap>>()
    val generateStatus: LiveData<Resource<Bitmap>>
        get() = _generate

    fun generateQRCode(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
           val result =  useCase.generateQRCode(url, bitmap)
            _generate.postValue(result)
        }
    }

    companion object{
        const val width= 500
        const val height = 500
    }
}