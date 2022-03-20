package com.mrshafiee.babbelcodechallenge

import com.google.common.truth.Truth.assertThat
import com.mrshafiee.babbelcodechallenge.model.Word
import org.junit.Before
import org.junit.Test

class WordUtilsTest {

    private var wordUtils: WordUtils? = null

    @Before
    fun setUp() {
        wordUtils = WordUtils()
    }

    @Test
    fun convertJsonStringToListOfWordsTest() {
        val testJsonString = """
            [
              {
                "text_eng": "primary school",
                "text_spa": "escuela primaria"
              },
              {
                "text_eng": "teacher",
                "text_spa": "profesor / profesora"
              },
              {
                "text_eng": "pupil",
                "text_spa": "alumno / alumna"
              }
            ]
        """.trimIndent()

        val expectedWordList = listOf(
            Word("primary school", "escuela primaria"),
            Word("teacher", "profesor / profesora"),
            Word("pupil", "alumno / alumna")
        )

        val result = wordUtils?.convertJsonStringToWordList(testJsonString)
        assertThat(result).isEqualTo(expectedWordList)
    }

    @Test
    fun checkTheCorrectAnswerTest() {
        val wordList = listOf(
            Word("primary school", "escuela primaria"),
            Word("teacher", "profesor / profesora"),
            Word("pupil", "alumno / alumna")
        )
        val result = wordUtils?.checkTheCorrectAnswer(
            wordList,
            "pupil",
            "alumno / alumna"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun checkTheWrongAnswerTest() {
        val wordList = listOf(
            Word("primary school", "escuela primaria"),
            Word("teacher", "profesor / profesora"),
            Word("pupil", "alumno / alumna")
        )
        val result = wordUtils?.checkTheWrongAnswer(
            wordList,
            "teacher",
            "alumno / alumna"
        )
        assertThat(result).isTrue()
    }

}