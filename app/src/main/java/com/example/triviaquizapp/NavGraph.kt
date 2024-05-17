package com.example.triviaquizapp


import EndScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

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
                QuestionScreen(questionList,navController)
            }
        }
        composable(
            route = Screen.LastScreen.route +"/{questionCount}/{score}/{correctQuestion}",
            arguments = listOf(
                navArgument("questionCount"){type = NavType.IntType},
                navArgument("score"){type = NavType.IntType},
                navArgument("correctQuestion"){type = NavType.IntType}
            )
        ){
            backStackEntry->
                val questionCount = backStackEntry.arguments?.getInt("questionCount") ?:0
                val score = backStackEntry.arguments?.getInt("score") ?: 0
                val correctQuestion = backStackEntry.arguments?.getInt("correctQuestion") ?: 0
             EndScreen(questionCount,score,correctQuestion)
        }
    }
}