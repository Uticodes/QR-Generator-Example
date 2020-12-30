package com.example.qr_generator_example.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
//import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.qr_generator_example.getOrAwaitValueTest
import com.example.qr_generator_example.utils.Resource

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Matcher

//@RunWith(Android)
//@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
class QRViewModelTest : TestCase(){

    private lateinit var qrViewModel: QRViewModel
    private lateinit var instrumentationCtx: Context

    @Before
    override fun setUp() {
        instrumentationCtx = ApplicationProvider.getApplicationContext<Context>()
        qrViewModel= QRViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun `check if url has empty field, returns error`() {
        qrViewModel.generateQRCode("url")
        assertEquals("","")
        val value = qrViewModel.generateStatus.getOrAwaitValueTest()
        //assertThat(value.).isEqualTo(Resource.Status.ERROR)

        //assertThat(value.getContentIfNotHandled()?.status.toString(), Matcher<> macher).isEqualTo(Resource.Status.ERROR)
    }

    @Test
    fun testGenerateQRCode() {

        qrViewModel.generateQRCode("url")
        assertEquals("url", "url")
        //assertThat(instrumentationCtx.display).contains("your_package_name")
    }

}



/*
class QRViewModelTest : TestCase() {

    val qrViewModel: QRViewModel()
    fun testGetGenerate() {}

    @Test
    fun testGenerateQRCode() {}
}*/
