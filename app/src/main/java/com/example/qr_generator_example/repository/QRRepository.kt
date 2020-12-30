package com.example.qr_generator_example.repository

import android.app.Application
import com.example.qr_generator_example.data.QRDataSource
import com.example.qr_generator_example.data.remote.QRRemoteDataSource
import com.example.qr_generator_example.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import com.example.qr_generator_example.utils.Result.Success

class QRRepository private constructor(application: Application) {

    private val qrRemoteDataSource: QRDataSource
    //private val qrksLocalDataSource: QRDataSource
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO


    companion object {
        @Volatile
        private var INSTANCE: QRRepository? = null

        fun getRepository(app: Application): QRRepository {
            return INSTANCE ?: synchronized(this) {
                QRRepository(app).also {
                    INSTANCE = it
                }
            }
        }
    }

    init {
        /*val database = Room.databaseBuilder(application.applicationContext,
                ToDoDatabase::class.java, "Tasks.db")
                .build()*/

        qrRemoteDataSource = QRRemoteDataSource
        //qrksLocalDataSource = QRLoc(database.taskDao())
    }

    suspend fun generateQrCodeFromRemoteDataSource() {
        val url: String? = null
        val remoteTasks = qrRemoteDataSource.generateQRCode(url!!)

        if (remoteTasks is Success) {
            // Real apps might want to do a proper sync.
            //qrksLocalDataSource.generateQRCode(url)
            remoteTasks.data.forEach { task ->
                //qrksLocalDataSource.generateQRCode(task.toString())
            }
        } else if (remoteTasks is Result.Error) {
            throw remoteTasks.exception
        }
    }
}