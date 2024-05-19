package com.example.triviaquizapp

data class QuestionOutput(
    val question: String,
    val correctAnswer: String,
    val options: List<String>,
    val category: String
)
