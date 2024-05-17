package com.example.triviaquizapp


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            FrontPage(navController = navController)
        }
        composable(route = Screen.Question.route) {
            val questionList =
                navController.previousBackStackEntry?.savedStateHandle?.get<List<QuestionOutput>>("questionList")
            if (questionList != null) {
                QuestionScreen(questionList)
            }
        }
    }
}