package com.mrshafiee.babbelcodechallenge

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mrshafiee.babbelcodechallenge.model.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val wordUtils: WordUtils
) : ViewModel() {

    private val wordList = mutableListOf<Word>()

    private val _originalWordStateFlow = MutableStateFlow("")
    val originalWord = _originalWordStateFlow.asStateFlow()

    private val _translatedWordStateFlow = MutableStateFlow("")
    val translatedWord = _translatedWordStateFlow.asStateFlow()

    private val _wrongAnswersStateFlow = MutableStateFlow(0)
    val wrongAnswersCounter = _wrongAnswersStateFlow.asStateFlow()

    private val _correctAnswersStateFlow = MutableStateFlow(0)
    val correctAnswersCounter = _correctAnswersStateFlow.asStateFlow()

    private val _noAnswersStateFlow = MutableStateFlow(0)
    val noAnswersCounter = _noAnswersStateFlow.asStateFlow()


    fun loadWordListFromJson(json: String) {
        wordList.addAll(wordUtils.convertJsonStringToWordList(json))
    }

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

    fun provideNewOriginalWord() {
        _originalWordStateFlow.value = wordUtils.pickRandomOriginalWord(wordList)
    }

    fun provideNewTranslatedWord() {
        _translatedWordStateFlow.value = wordUtils.pickRandomTranslatedWord(wordList)
    }

    fun onCorrectButtonClicked() =
        wordUtils.checkTheCorrectAnswer(wordList, originalWord.value, translatedWord.value)

    fun onWrongButtonClicked() =
        wordUtils.checkTheWrongAnswer(wordList, originalWord.value, translatedWord.value)

    fun increaseCorrectAnswersCounter() {
        _correctAnswersStateFlow.value++
    }

    fun increaseWrongAnswersCounter() {
        _wrongAnswersStateFlow.value++
    }

    fun increaseNoAnswersCounter() {
        _noAnswersStateFlow.value++
    }
}