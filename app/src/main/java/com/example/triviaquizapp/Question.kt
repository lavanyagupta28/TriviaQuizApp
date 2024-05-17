package com.example.triviaquizapp

data class Question(
    val category: Category,
    val type: Type,
    val difficulty: Difficulty,
    val question: String,
    val correctAns: String,
    val incorrectAns: List<String>
)
