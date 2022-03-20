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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.loadJSONFromAsset(this, "Words.json")?.let {
            viewModel.loadWordListFromJson(it)
        }

        viewModel.provideNewOriginalWord()
        viewModel.provideNewTranslatedWord()

        lifecycleScope.launch {
            viewModel.originalWord.collectLatest {
                binding.tvOriginalWord.text = it
            }
        }

        lifecycleScope.launch {
            viewModel.translatedWord.collectLatest {
                val wordTextView = addTranslatedWordOnBoard(it)
                moveTranslatedWordToBottom(wordTextView)
            }
        }

        lifecycleScope.launch {
            viewModel.noAnswersCounter.collectLatest {
                binding.tvNoAnswersCount.text = it.toString()
            }
        }
        lifecycleScope.launch {
            viewModel.wrongAnswersCounter.collectLatest {
                binding.tvWrongAnswersCount.text = it.toString()
            }
        }
        lifecycleScope.launch {
            viewModel.correctAnswersCounter.collectLatest {
                binding.tvCorrectAnswersCount.text = it.toString()
            }
        }
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
        lifecycleScope.launch {
            viewModel.startObjectMovement(
                1.0f
            ).collectLatest {
                val lp = view.layoutParams as ConstraintLayout.LayoutParams
                lp.verticalBias = it
                view.layoutParams = lp
            }
        }.invokeOnCompletion {
            onNoAnswer()
        }
    }

    private fun removeTranslatedWordFromBoard() {
        binding.clTranslatedWordContainer.removeAllViews()
    }

    private fun onNoAnswer() {
        viewModel.increaseNoAnswersCounter()
        removeTranslatedWordFromBoard()
        viewModel.provideNewOriginalWord()
        viewModel.provideNewTranslatedWord()
    }

    private fun onCorrectButtonClicked() {
        viewModel.increaseCorrectAnswersCounter()
    }

    private fun onWrongButtonClicked() {
        viewModel.increaseWrongAnswersCounter()
    }
}