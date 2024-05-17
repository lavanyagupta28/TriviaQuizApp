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
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Questionscreen(question:Question){
    val options = question.incorrectAns.toMutableList().apply {
        add(question.correctAns)
        shuffle() // Shuffle the options

    }
    var selectedAnswer by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .background(Color(android.graphics.Color.parseColor("#1E1E1E"))),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = "${question.category.displayName}",
                color = Color.White,
                fontSize = 25.sp,
            )
        }
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        Row(horizontalArrangement = Arrangement.Start){
            Text(
                text = "Score",
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
        Text(
            text = question.question,
            color = Color.White
        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )

        options.forEachIndexed { index, option ->
            Button(
                onClick = {
                        if(!isSubmitted) {
                        selectedAnswer = option
                            }
                          },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    if(isSubmitted && option == question.correctAns)Color.Green
                    else if(isSubmitted && option == selectedAnswer && option != question.correctAns) Color.Red
                    else {
                        Color(android.graphics.Color.parseColor("#d7f1f1"))
                    },

                )
            ) {
                Text(
                    text = option,
                    color = Color.Black
                )
            }
        }
        Spacer(
            modifier = Modifier.weight(1f)
        )
            Button(
                onClick = {
                    isSubmitted = true
                },
                colors = ButtonDefaults.buttonColors(

                    Color(android.graphics.Color.parseColor("#FF7F00"))
                ),
                modifier = Modifier.fillMaxWidth()
                    .height(80.dp)
                    .padding(bottom = 10.dp)
            ) {
                Text(
                    text = "SUBMIT",
                    color = Color.White
                )
            }
            


    }

}

@Preview
@Composable
fun QuestionScreenPreview(){
   val question = Question(
       Category.ENTERTAINMENT_VIDEO_GAMES, Type.MULTIPLE_CHOICE, Difficulty.MEDIUM,
        "Which of these characters was almost added into Super Smash Bros. Melee, but not included as the game was too far in development?",
        "Solid Snake",
        listOf("Pit", "Meta Knight", "R.O.B.")
    )
   Questionscreen(question)
}