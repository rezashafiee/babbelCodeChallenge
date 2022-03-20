package com.mrshafiee.babbelcodechallenge

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.lifecycleScope
import com.mrshafiee.babbelcodechallenge.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var json: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        json = viewModel.loadJSONFromAsset(this, "Words.json")

        val wordTextView = addTranslatedWordOnBoard("Imchini")
        moveTranslatedWordToBottom(wordTextView)
    }

    private fun addTranslatedWordOnBoard(text: String): View {
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
        return wordTextView
    }

    private fun moveTranslatedWordToBottom(view: View) {
        lifecycleScope.launch(Main) {
            viewModel.startObjectMovement(
                1.0f
            ).collectLatest {
                val lp = view.layoutParams as ConstraintLayout.LayoutParams
                lp.verticalBias = it
                view.layoutParams = lp
            }
        }
    }

    private fun removeTranslatedWordFromBoard() {

    }
}