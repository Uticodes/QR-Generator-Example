package com.example.qr_generator_example.viewModel


import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.qr_generator_example.MainCoroutineRule
import com.example.qr_generator_example.getOrAwaitValueTest
import com.example.qr_generator_example.usecases.GenerateQRCodeUseCase
import com.google.zxing.MultiFormatWriter
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify


@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
class QRViewModelTest : TestCase(){

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Rule
    var instantExecutorRule: InstantTaskExecutorRule? = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private var qrViewModel: QRViewModel? = null

    private val mockMultiFormatWriter = Mockito.mock(MultiFormatWriter::class.java)
    @Mock
    private var useCase = GenerateQRCodeUseCase(mockMultiFormatWriter)

    private val bitmap = Mockito.mock(Bitmap::class.java)

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        //MockitoAnnotations.initMocks(this)
        qrViewModel= QRViewModel(ApplicationProvider.getApplicationContext())
    }

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
        val value = qrViewModel?.generateStatus?.getOrAwaitValueTest()
    }

    @Test
    fun testGenerateQRCode() {

        //`when`(qrViewModel?.generateQRCode("url")).thenReturn()
         qrViewModel?.generateQRCode("url")
        verify(useCase).generateQRCode("url", bitmap)

    }

}

