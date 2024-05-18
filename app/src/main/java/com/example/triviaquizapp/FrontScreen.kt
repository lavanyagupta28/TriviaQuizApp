package com.example.triviaquizapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun FrontPage(navController: NavController) {

    var questionCount = 10 //TODO: make it variable
    var difficultyLevel by remember { mutableIntStateOf(0) }
    var category: Category by remember { mutableStateOf(Category.ANY_CATEGORY) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.Black)
            .padding(8.dp)
    ) {
        Text(
            text = "Quiz App",
            fontSize = 30.sp,
            color = Color.White,
            modifier = Modifier.padding(10.dp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color(android.graphics.Color.parseColor("#FF7F00")))
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "Question Count",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = questionCount.toString(),
                color = Color.White,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        // Difficulty Level 1 for easy
        Box(
            modifier = Modifier
                .background(Color(android.graphics.Color.parseColor("#1E1E1E")))
                .padding(16.dp)
                .fillMaxWidth()
                .clickable { difficultyLevel = (difficultyLevel + 1) % 3 }
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp)),

            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Difficulty Level: ${difficultyLevel} ",
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .background(
                    if (category == Category.SPORTS) Color(
                        android.graphics.Color.parseColor(
                            "#FF7F00"
                        )
                    ) else Color(android.graphics.Color.parseColor("#1E1E1E"))
                )
                .padding(16.dp)
                .fillMaxWidth()
                .clickable { category = Category.SPORTS }
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp)),

            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Sports",
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .background(
                    if (category == Category.ENTERTAINMENT_VIDEO_GAMES) Color(
                        android.graphics.Color.parseColor(
                            "#FF7F00"
                        )
                    ) else Color(android.graphics.Color.parseColor("#1E1E1E"))
                )
                .padding(16.dp)
                .fillMaxWidth()
                .clickable { category = Category.ENTERTAINMENT_VIDEO_GAMES }
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp)),

            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Entertainment: Video Games",
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .background(Color(android.graphics.Color.parseColor("#d7f1f1")))
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    val questionList = getQuestions(difficultyLevel, category, questionCount)
                    navController.currentBackStackEntry?.savedStateHandle?.set( // TODO: use viewModel
                        key = "questionList",
                        value = questionList
                    )
                    navController.navigate(Screen.Question.route)
                }
                .height(60.dp)
                .padding(vertical = 16.dp, horizontal = 32.dp)
                .clip(RoundedCornerShape(16.dp)),

            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "START QUIZ",
                color = Color.Black,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

fun getQuestions(
    difficultyLevel: Int,
    category: Category,
    questionCount: Int
): List<QuestionOutput> {
//    val jsonString = HardcodedQuestions.jsonString
//    val gson = Gson()
//    val questionInput = gson.fromJson(jsonString, QuestionInput::class.java )

    val questionInputList = HardcodedQuestions.questionInputs //TODO: get questions from API call
    return getShuffledOptions(questionInputList)
}

fun getShuffledOptions(questionInputList: List<QuestionInput>): List<QuestionOutput> {
    val questionOutputList = mutableListOf<QuestionOutput>()
    questionInputList.forEach { questionInput ->
        val options = questionInput.incorrectAns.toMutableList().apply {
            add(questionInput.correctAns)
            shuffle()
        }
        questionOutputList.add(
            QuestionOutput(
                questionInput.question,
                questionInput.correctAns,
                options,
                questionInput.category
            )
        )
    }
    return questionOutputList
}


@Preview(showBackground = true)
@Composable
fun FrontPagePreview() {
    val navController = rememberNavController()
    FrontPage(
        navController
    )
}




