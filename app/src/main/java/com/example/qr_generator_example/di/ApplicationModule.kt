package com.example.qr_generator_example.di

import com.example.qr_generator_example.usecases.GenerateQRCodeUseCase
import com.example.qr_generator_example.viewModel.QRViewModel
import com.google.zxing.MultiFormatWriter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Factory instance of GenerateQRCodeUseCase
    factory { GenerateQRCodeUseCase(get()) }
    factory { MultiFormatWriter() }
    // ViewModel for QRViewModel View
    viewModel { QRViewModel(get()) }
}