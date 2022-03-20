package com.mrshafiee.babbelcodechallenge

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun loadJsonFromAssetTest() {
        val expectedResult =
            "{\"array\": [{\"id\": 1, \"name\": \"object1\"}, {\"id\": 2, \"name\": \"object2\"}]}"
        val result = viewModel.loadJSONFromAsset(
            ApplicationProvider.getApplicationContext(),
            "Test.json"
        )
        assertThat(result).isEqualTo(expectedResult)
    }
}