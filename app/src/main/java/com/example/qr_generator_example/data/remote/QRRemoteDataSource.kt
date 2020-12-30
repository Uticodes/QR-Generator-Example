package com.example.qr_generator_example.data.remote

import com.example.qr_generator_example.data.QRDataSource
import com.example.qr_generator_example.model.QREntity
import com.example.qr_generator_example.utils.Result
import kotlinx.coroutines.delay

/**
 * Implementation of the data source that adds a latency simulating network.
 */
object QRRemoteDataSource : QRDataSource{

    private const val SERVICE_LATENCY_IN_MILLIS = 2000L

    private var TASKS_SERVICE_DATA = LinkedHashMap<String, String>(2)

    override suspend fun generateQRCode(url: String): Result<String> {
        delay(SERVICE_LATENCY_IN_MILLIS)
        //return Result.Success(url)

        TASKS_SERVICE_DATA[url]?.let {
            return Result.Success(it)
        }
        return Result.Error(Exception("Task not found"))
    }
}