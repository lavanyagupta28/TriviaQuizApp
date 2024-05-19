package com.example.triviaquizapp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviaquizapp.model.QuestionInput
import com.example.triviaquizapp.network.ApiInterface
import kotlinx.coroutines.launch

class QuestionViewModel : ViewModel() {
    var questionListResponse: List<QuestionInput> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

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
