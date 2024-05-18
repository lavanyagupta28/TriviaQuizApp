package com.example.triviaquizapp

data class QuestionInput(
    val category: Category,
    val type: Type,
    val difficulty: Difficulty,
    val question: String,
    val correctAns: String,
    val incorrectAns: List<String>
)