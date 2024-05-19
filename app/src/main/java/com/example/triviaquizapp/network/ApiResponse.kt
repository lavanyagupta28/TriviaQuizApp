package com.example.triviaquizapp.network

import com.example.triviaquizapp.model.QuestionInput

data class ApiResponse(
    val response_code: Int,
    val results: List<QuestionInput>
)
