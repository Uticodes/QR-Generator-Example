package com.example.qr_generator_example

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidmads.library.qrgenearator.QRGSaver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.zxing.WriterException
import java.io.File
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var btnGenQRCode: Button
    private lateinit var btnSaveToDevice: Button
    private lateinit var qrImage: ImageView
    private lateinit var textInput: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnGenQRCode = findViewById(R.id.btnGenQRCode)
        textInput = findViewById(R.id.etInput)
        qrImage = findViewById(R.id.qrImageView)
        btnSaveToDevice = findViewById(R.id.btnSaveToDevice)

        btnGenQRCode.setOnClickListener {
            generateQRCode()
        }

        btnSaveToDevice.setOnClickListener {
            saveToFile()
        }

    }

    private fun generateQRCode() {

        if (TextUtils.isEmpty(textInput.text)) {
            textInput.error = "Kindly Enter Url"
            //Hide QR ImageView
            qrImage.isVisible = false
            //Hide save QR code button
            btnSaveToDevice.isVisible = false
            return
        }
        try {
            //show QR ImageView
            qrImage.isVisible = true
            //show save QR code button
            btnSaveToDevice.isVisible = true

            val qrgEncoder = QRGEncoder(textInput.text.toString(), null, QRGContents.Type.TEXT, 150)
            val bitmap = qrgEncoder.encodeAsBitmap()
            qrImage.setImageBitmap(bitmap)

            //Close Soft Keyboard
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    private fun saveToFile() {

        if (TextUtils.isEmpty(textInput.text)) {
            textInput.setError("enter the text or email or link")
            return
        }
        try {
            val qrgEncoder = QRGEncoder(textInput.text.toString(), null, QRGContents.Type.TEXT, 150)
            val bitmap = qrgEncoder.encodeAsBitmap()
            qrImage.setImageBitmap(bitmap)
            var fileName = "QRCode_" + System.currentTimeMillis() + ".jpg"
            val file = File(Environment.getExternalStorageDirectory(), fileName)
            file.createNewFile()
            val saveLocation = file.parent + File.separator
            fileName = file.name.substring(0, file.name.indexOf("."))
            QRGSaver.save(saveLocation, fileName, bitmap, QRGContents.ImageType.IMAGE_JPEG)
            Toast.makeText(
                this,
                "QR Code successfully saved in the external storage!",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: WriterException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun permissionCheck() {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                123
            )
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