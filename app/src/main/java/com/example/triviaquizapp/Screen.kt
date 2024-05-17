package com.example.triviaquizapp

sealed class Screen(val route: String) {
    object Home: Screen(route = "home_screen")
    object Question: Screen(route="question_screen")
}