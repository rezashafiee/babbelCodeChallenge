package com.mrshafiee.babbelcodechallenge

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private var json: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        json = viewModel.loadJSONFromAsset(this, "Words.json")
    }
}