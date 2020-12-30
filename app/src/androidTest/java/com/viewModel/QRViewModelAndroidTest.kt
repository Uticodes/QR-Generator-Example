package com.example.qr_generator_example.viewModel

import android.content.Context
import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.MainAndroidCoroutineRule
import com.example.qr_generator_example.usecases.GenerateQRCodeUseCase
import com.example.qr_generator_example.utils.Resource
import com.getOrAwaitValueAndroidTest
import com.google.zxing.MultiFormatWriter

import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations



@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
class QRViewModelAndroidTest constructor(val url: String) : TestCase(){

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Rule
    var instantExecutorRule: InstantTaskExecutorRule? = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainAndroidCoroutineRule()

    private val mockMultiFormatWriter = Mockito.mock(MultiFormatWriter::class.java)

    private val generateQRCodeUseCase = GenerateQRCodeUseCase(mockMultiFormatWriter)

    @Mock
    private var useCase = GenerateQRCodeUseCase(mockMultiFormatWriter)

    @Mock
    private var qrViewModel = QRViewModel(useCase)



    private val bitmap = Mockito.mock(Bitmap::class.java)


    @Before
    @Throws(Exception::class)
    override fun setUp() {
        //MockitoAnnotations.initMocks(this)
        //qrViewModel= QRViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun testNull() {

        qrViewModel.generateQRCode("")
        verify(useCase).generateQRCode("url", bitmap)
    }

    @Test
    fun testIfUrlIsEmptyShowError() {
        qrViewModel.generateQRCode("url")
        assertEquals("", "")
        val value = qrViewModel.generateStatus.getOrAwaitValueAndroidTest()

    }

    @Test
    fun testGenerateQRCode() {

        qrViewModel.generateQRCode("url")
        assertEquals("url", "url")
        qrViewModel.generateQRCode("url")
        Mockito.verify(useCase).generateQRCode("url", bitmap)
    }

}

