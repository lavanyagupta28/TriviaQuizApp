package com.example.triviaquizapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.triviaquizapp.viewModel.QuestionViewModel


@Composable
fun QuestionScreen(questionOutputList: List<QuestionOutput>, navController: NavController, questionViewModel : QuestionViewModel = viewModel()) {

    var score by rememberSaveable { mutableStateOf(questionViewModel.score) }
    var correctQuestions by rememberSaveable { mutableStateOf(questionViewModel.correctQuestions) }
    var currentIndex by rememberSaveable { mutableStateOf(questionViewModel.currentIndex) }
    var selectedAnswer by remember { mutableStateOf(questionViewModel.selectedAnswer) }
    var isSubmitted by remember { mutableStateOf(questionViewModel.isSubmitted) }
    var isNext by remember { mutableStateOf(questionViewModel.isNext) }
    var isSelectedOnce by remember { mutableStateOf(questionViewModel.isSelectedOnce) }

    if (currentIndex >= questionOutputList.size) {
        val questionCount = questionOutputList.size
        navController.navigate(
            "${Screen.LastScreen.route}/$questionCount/$score/$correctQuestions"
        )
        return
    }
    val question = questionOutputList[currentIndex]
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth()
                .background(Color(android.graphics.Color.parseColor("#1E1E1E"))),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = question.category,
                color = Color.White,
                fontSize = 23.sp,
            )
        }
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        Row(horizontalArrangement = Arrangement.Start) {
            Text(
                text = "Score: $score",
                color = Color.White,
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            )
        }
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Divider(
            color = Color.White,
        )
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        Row {
            Text(
                text = "Q${currentIndex + 1} ",
                color = Color.White

            )
            Text(
                text = question.question,
                color = Color.White
            )
        }
        Spacer(
            modifier = Modifier.height(30.dp)
        )

        question.options.forEachIndexed { _, option ->
            OptionButton(
                option = option,
                isSubmitted = isSubmitted,

                correctAnswer = question.correctAnswer,
                selectedAnswer = selectedAnswer,
                onOptionSelected = {
                    if (!isSubmitted) {
                        isSelectedOnce = true
                        questionViewModel.isSelectedOnce = true
                        selectedAnswer = it
                        questionViewModel.selectedAnswer = it
                    }
                }
            )
        }
        Spacer(
            modifier = Modifier.weight(1f)
        )

        SubmitButton(
            onClick = {
                if (isSubmitted && isSelectedOnce) { //
                    // they clicked on next button
                    currentIndex++
                    questionViewModel.currentIndex++
                    isSubmitted = false
                    questionViewModel.isSubmitted = false
                    isNext = false
                    questionViewModel.isNext = false
                    isSelectedOnce = false
                    questionViewModel.isSelectedOnce = false
                }
                if (isSelectedOnce) {
                    isSubmitted = true
                    questionViewModel.isSubmitted = true
                    if (selectedAnswer == question.correctAnswer) {
                        score += 10
                        questionViewModel.score += 10
                        correctQuestions += 1
                        questionViewModel.correctQuestions += 1
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(bottom = 10.dp),
            isSubmitted = isSubmitted,
            isSelectedOnce = isSelectedOnce
        )


    }

}

@Composable
fun OptionButton(
    option: String,
    isSubmitted: Boolean,
    correctAnswer: String,
    selectedAnswer: String,
    onOptionSelected: (String) -> Unit
) {
    Button(
        onClick = { onOptionSelected(option) },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(
            when {
                isSubmitted && option == selectedAnswer && option == correctAnswer -> Color.Green
                isSubmitted && option == selectedAnswer && option != correctAnswer -> Color.Red
                !isSubmitted && option == selectedAnswer -> Color(
                    android.graphics.Color.parseColor(
                        "#FF7F00"
                    )
                )

                else -> Color(android.graphics.Color.parseColor("#d7f1f1"))
            }
        )
    ) {
        Text(
            text = option,
            color = Color.Black
        )
    }

}


@Composable
fun SubmitButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSubmitted: Boolean,
    isSelectedOnce: Boolean
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            Color(android.graphics.Color.parseColor("#FF7F00"))
        ),
        modifier = modifier
    ) {
        Text(
            text = if (isSubmitted && isSelectedOnce) "NEXT" else "SUBMIT",
            color = Color.White
        )
    }
}


@Preview
@Composable
fun QuestionScreenPreview() {
    val questionInput = QuestionOutput(
        "Which of these characters was almost added into Super Smash Bros. Melee, but not included as the game was too far in development?",
        "Solid Snake",
        listOf("Pit", "Meta Knight", "R.O.B.", "Solid Snake"), "ENTERTAINMENT_VIDEO_GAMES"
    )
    val navController = rememberNavController()
    QuestionScreen(listOf(questionInput), navController)

}