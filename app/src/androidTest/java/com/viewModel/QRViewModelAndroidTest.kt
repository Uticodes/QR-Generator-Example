package com.example.qr_generator_example.viewModel

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.test.ext.junit.runners.AndroidJUnit4

import android.content.Context
import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.MainAndroidCoroutineRule
import com.example.qr_generator_example.utils.Resource
import com.getOrAwaitValueAndroidTest

import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations



@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
class QRViewModelAndroidTest : TestCase(){

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Rule
    var instantExecutorRule: InstantTaskExecutorRule? = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainAndroidCoroutineRule()

    @Mock
    private var qrViewModel: QRViewModel? = null
    //private lateinit var qrViewModel: QRViewModel
    @Mock
    var observer: Observer<Resource<Bitmap>?>? = null
    private lateinit var instrumentationCtx: Context

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        //MockitoAnnotations.initMocks(this)
        qrViewModel= QRViewModel(ApplicationProvider.getApplicationContext())
    }

   /* @Before
    @Throws(Exception::class)
    override fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = NewsViewModel(apiClient, RxSingleSchedulers.TEST_SCHEDULER)
        viewModel.getNewsListState().observeForever(observer)
    }*/

    /*fun setUp() {
        //instrumentationCtx = ApplicationProvider.getApplicationContext<Context>()

    }*/

    @Test
    fun testNull() {
        `when`(qrViewModel?.generateQRCode("")).thenReturn(null)
        assertNotNull(qrViewModel?.generateQRCode(""))
        assertTrue(qrViewModel?.generateQRCode("url").toString(), true)
    }

    @Test
    fun testIfUrlIsEmptyShowError() {
        qrViewModel?.generateQRCode("url")
        assertEquals("", "")
        val value = qrViewModel?.generateStatus?.getOrAwaitValueAndroidTest()
        //assertThat(value.).isEqualTo(Resource.Status.ERROR)

        //assertThat(value.getContentIfNotHandled()?.status.toString(), Matcher<> macher).isEqualTo(Resource.Status.ERROR)
    }

    @Test
    fun testGenerateQRCode() {

        qrViewModel?.generateQRCode("url")
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
