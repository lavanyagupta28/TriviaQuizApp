package com.example.triviaquizapp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviaquizapp.Category
import com.example.triviaquizapp.model.QuestionInput
import com.example.triviaquizapp.network.ApiInterface
import kotlinx.coroutines.launch

class QuestionViewModel : ViewModel() {
    var questionCount: Int by mutableStateOf(5)
    var difficulty: String by mutableStateOf("Any Difficulty")
    var difficultyLevel: Int by mutableStateOf(0)
    var selectedCategory: Category by mutableStateOf(Category.ANY_CATEGORY)
    var questionListResponse: List<QuestionInput> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    var score by  mutableStateOf(0)
    var correctQuestions by mutableStateOf(0)
    var currentIndex by mutableStateOf(0)
    var selectedAnswer by mutableStateOf("")
    var isSubmitted by mutableStateOf(false)
    var isNext by mutableStateOf(false)
    var isSelectedOnce by mutableStateOf(false)

    fun getQuestionList(amount: Int, category: Int?, difficulty: String?, type: String?) {
        viewModelScope.launch {
            val apiInterface = ApiInterface.getInstance()
            try {
                val response = apiInterface.getData(amount, category, difficulty, type)
                if (response.isSuccessful) {
                    questionListResponse = response.body()?.results ?: listOf()
                } else {
                    errorMessage = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

}


