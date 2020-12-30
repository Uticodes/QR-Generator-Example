package com.example.qr_generator_example.data

import com.example.qr_generator_example.model.QREntity
import com.example.qr_generator_example.utils.Result

/**
 * Main entry point for accessing tasks data.
 */
interface QRDataSource {
    suspend fun generateQRCode(url: String): Result<String>
}


