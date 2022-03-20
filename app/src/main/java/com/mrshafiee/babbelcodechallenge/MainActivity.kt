package com.mrshafiee.babbelcodechallenge

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.mrshafiee.babbelcodechallenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var json: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        json = viewModel.loadJSONFromAsset(this, "Words.json")
        addTranslatedWordOnBoard("Imchini")
    }

    private fun addTranslatedWordOnBoard(text: String) {
        val wordTextView = TextView(this)
        wordTextView.id = View.generateViewId()
        wordTextView.text = text
        wordTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0f)
        wordTextView.gravity = Gravity.CENTER

        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        layoutParams.verticalBias = 0f
        binding.clTranslatedWordContainer.addView(wordTextView, layoutParams)

        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.clTranslatedWordContainer)
        constraintSet.connect(
            wordTextView.id,
            ConstraintSet.TOP,
            binding.clTranslatedWordContainer.id,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            wordTextView.id,
            ConstraintSet.BOTTOM,
            binding.clTranslatedWordContainer.id,
            ConstraintSet.BOTTOM
        )

        constraintSet.applyTo(binding.clTranslatedWordContainer)
    }

    private fun moveTranslatedWordToBottom() {

    }

    private fun removeTranslatedWordFromBoard() {

    }
}