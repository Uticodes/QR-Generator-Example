package com.example.qr_generator_example.usecases

import android.graphics.Bitmap
import android.os.Build
import com.example.qr_generator_example.utils.Resource
import com.google.zxing.MultiFormatWriter
import junit.framework.TestCase

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE, sdk = [ Build.VERSION_CODES.P ])
@RunWith(RobolectricTestRunner::class)
class GenerateQRCodeUseCaseTest {

     private val mockMultiFormatWriter = mock(MultiFormatWriter::class.java)
    private val generateQRCodeUseCase = GenerateQRCodeUseCase(mockMultiFormatWriter)
    private val bitmap = mock(Bitmap::class.java)

    @Test
    fun testIfUrlIsEmpty(){
       val result = generateQRCodeUseCase.generateQRCode("", bitmap)
        assertEquals( Resource.Status.ERROR, result.status)
        assertEquals("QR code ur should not be empty", result.message)
    }

    @Test
    fun testThatGenerateQRCodeIsSuccessful(){
        val result = generateQRCodeUseCase.generateQRCode("url", bitmap)
        assertEquals(Resource.Status.SUCCESS, result.status)
        assertEquals("QR code generate successfully", result.message)
    }

}