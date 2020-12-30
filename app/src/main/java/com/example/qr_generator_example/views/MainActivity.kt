package com.example.qr_generator_example.views

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidmads.library.qrgenearator.QRGSaver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.qr_generator_example.R
import com.example.qr_generator_example.hideKeyboard
import com.example.qr_generator_example.permissionCheck
import com.example.qr_generator_example.utils.Resource
import com.example.qr_generator_example.viewModel.QRViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import java.io.File
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var btnGenQRCode: Button
    private lateinit var btnSaveToDevice: Button
    private lateinit var qrImage: ImageView
    private lateinit var textInput: EditText
    private lateinit var qrViewModel: QRViewModel
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnGenQRCode = findViewById(R.id.btnGenQRCode)
        textInput = findViewById(R.id.etInput)
        qrImage = findViewById(R.id.qrImageView)
        btnSaveToDevice = findViewById(R.id.btnSaveToDevice)
        progressBar = findViewById(R.id.progress)

        //initialize viewModel
        qrViewModel= ViewModelProvider(this).get(QRViewModel::class.java)

        permissionCheck()

        btnGenQRCode.setOnClickListener {
            if (TextUtils.isEmpty(textInput.text)) {
                textInput.error = "Kindly Enter Url"
                //set QR ImageView to gone
                qrImage.isVisible = false
                //set save QR code button to gone
                btnSaveToDevice.isVisible = false
                progressBar.isVisible = false
            }else{
                try {
                    qrViewModel.generateQRCode(textInput.text.toString())
                    qrViewModel.generateStatus.observe(this, Observer {
                        when (it.status) {
                            Resource.Status.LOADING -> progressBar.isVisible = true
                            Resource.Status.SUCCESS -> {
                                qrImage.setImageBitmap(it.data)
                                Toast.makeText(this, it.message,Toast.LENGTH_LONG).show()
                            }
                            Resource . Status . ERROR -> {
                                progressBar.isVisible = false
                                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                                println(it.message!!)
                            }
                        }
                    })

                    //show QR ImageView
                    qrImage.isVisible = true

                    //Close Soft Keyboard
                    hideKeyboard()

                } catch (e: WriterException) {
                    e.printStackTrace()

                }
            }


        }

    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults.isNotEmpty()) {
                for (i in grantResults.indices)
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
                        finish()
            } else {
                finish()
            }
        }
    }
}