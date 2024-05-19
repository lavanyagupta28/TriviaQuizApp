package com.example.triviaquizapp

import android.text.Html
import android.util.JsonReader
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.triviaquizapp.model.QuestionInput
import com.example.triviaquizapp.viewModel.QuestionViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

@Composable
fun FrontPage(navController: NavController) {
    val questionViewModel: QuestionViewModel = viewModel()
    var questionCount:Int by remember { mutableStateOf(5) }//TODO: make it variable
    var difficulty: String by remember { mutableStateOf("Any Difficulty") }
    var difficultyLevel: Int by remember { mutableStateOf(0) }
    var category: Category by remember { mutableStateOf(Category.ANY_CATEGORY) }
    var selectedCategory: Category by  remember { mutableStateOf(Category.ANY_CATEGORY) }
    val categories:List<Category> = Category.getAllCategories()

    val questionInputList = questionViewModel.questionListResponse
    if (questionInputList.isNotEmpty()) {
        navController.currentBackStackEntry?.savedStateHandle?.set( // TODO: use viewModel
            key = "questionList",
            value = getShuffledOptions(questionInputList)
        )
        navController.navigate(Screen.Question.route)
    }
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
                .clickable { questionCount = questionCountManager(questionCount) }
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
                .clickable { difficultyLevel = (difficultyLevel + 1) % 4 }
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp)),

            contentAlignment = Alignment.Center
        ) {
            Text(
                text = when (difficultyLevel) {
                    0 -> "Difficulty: Any Difficulty"
                    1 -> "Difficulty: EASY"
                    2 -> "Difficulty: MEDIUM"
                    else -> "Difficulty: HARD"
                },
                color = Color.White
            )

        }
        LazyColumn(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .weight(1f)
        ){
          items(categories) { category ->
              Spacer(modifier = Modifier.height(10.dp))
              categoryUI(category = category , onCategorySelected = {
                  selectedCategory = category
              } , selectedCategory = selectedCategory)

          }
        }




        Box(
            modifier = Modifier
                .background(Color(android.graphics.Color.parseColor("#d7f1f1")))
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    Difficulty
                        .getDifficultyByDifficultyLevel(difficultyLevel)
                        ?.let {
                            difficulty = it.apiName
                        }
                    var difficultyApi: String? = difficulty
                    if (difficulty == "") {
                        difficultyApi = null
                    }
                    var categoryApi: Int? = selectedCategory.id
                    if(selectedCategory == Category.ANY_CATEGORY){
                        categoryApi = null
                    }
                    questionViewModel.getQuestionList(
                        questionCount,
                        categoryApi,
                        difficultyApi,
                        null
                    )

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


@Composable
fun categoryUI(category: Category, onCategorySelected:(Category)->Unit, selectedCategory: Category){
   Box(modifier = Modifier
       .background(
           if (category == selectedCategory) Color(
               android.graphics.Color.parseColor(
                   "#FF7F00"
               )
           ) else Color(android.graphics.Color.parseColor("#1E1E1E"))
       )
       .padding(8.dp)
       .fillMaxWidth()
       .clickable { onCategorySelected(category) }
       .padding(vertical = 8.dp, horizontal = 8.dp)
       .clip(RoundedCornerShape(8.dp)),

       contentAlignment = Alignment.Center){
       Text(
           text = category.displayName,
           color = Color.White
       )
   }

}
fun questionCountManager(questionCount: Int):Int {
    if(questionCount == 50){
        return 5
    }
 return (questionCount + 5)
}

/*suspend fun fetchQuestions(
    difficulty: Difficulty,
    category: Category,
    questionCount: Int,
    questionViewModel: QuestionViewModel
): List<QuestionOutput> = coroutineScope {
    val deferredQuestions = async {
        getQuestions(difficulty, category, questionCount, questionViewModel)
    }
    deferredQuestions.await()
}
*/

/*
fun getQuestions(
    difficulty: Difficulty,
    category: Category,
    questionCount: Int,
    viewModel: QuestionViewModel
): List<QuestionOutput> {
//    val jsonString = HardcodedQuestions.jsonString
//    val gson = Gson()
//    val questionInput = gson.fromJson(jsonString, QuestionInput::class.java )

    // val questionInputList = HardcodedQuestions.questionInputs //TODO: get questions from API call

    var shuffledOptions: List<QuestionOutput> = emptyList()
    if(difficulty.apiName == "Any Category"){

        }
    val questionInputList = viewModel.questionListResponse

    shuffledOptions = getShuffledOptions(questionInputList)
    return shuffledOptions
}
*/

fun getShuffledOptions(questionInputList: List<QuestionInput>?): List<QuestionOutput> {
    val questionOutputList = mutableListOf<QuestionOutput>()
    questionInputList?.forEach { questionInput ->
        val options = mutableListOf<String>()
        questionInput.incorrect_answers.forEach {
            options.add(decodeHtmlEntities(it))
        }
        options.apply {
            add(decodeHtmlEntities(questionInput.correct_answer))
            shuffle()
        }
        questionOutputList.add(
            QuestionOutput(
                decodeHtmlEntities(questionInput.question),
                decodeHtmlEntities(questionInput.correct_answer),
                options,
                decodeHtmlEntities(questionInput.category)
            )
        )
    }
    return questionOutputList
}

fun decodeHtmlEntities(text: String): String {
    return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString()
}


@Preview(showBackground = true)
@Composable
fun FrontPagePreview() {
    val navController = rememberNavController()
    FrontPage(
        navController
    )
}




