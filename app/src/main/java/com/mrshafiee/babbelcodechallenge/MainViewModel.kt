package com.mrshafiee.babbelcodechallenge

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class MainViewModel : ViewModel() {

    fun loadJSONFromAsset(context: Context, fileName: String): String? {
        val jsonContent: String
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            jsonContent = String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonContent
    }


    fun startObjectMovement(endPosition: Float) = flow {
        val startPosition = 0f
        var currentPosition = startPosition
        emit(startPosition)
        while (currentPosition <= endPosition) {
            delay(5)
            currentPosition += 0.001f
            emit(currentPosition)
        }
    }.flowOn(IO)
}