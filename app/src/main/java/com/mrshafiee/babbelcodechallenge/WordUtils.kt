package com.mrshafiee.babbelcodechallenge

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mrshafiee.babbelcodechallenge.model.Word

class WordUtils {

    fun convertJsonStringToWordList(jsonString: String): List<Word> {
        val wordListType = object : TypeToken<List<Word>>() {}.type
        return Gson().fromJson(jsonString, wordListType)
    }

    fun checkTheCorrectAnswer(
        words: List<Word>,
        englishText: String,
        spanishText: String
    ) = words.first { word: Word -> word.english == englishText }.spanish == spanishText


    fun checkTheWrongAnswer(
        words: List<Word>,
        englishText: String,
        spanishText: String
    ) = words.first { word: Word -> word.english == englishText }.spanish != spanishText
}