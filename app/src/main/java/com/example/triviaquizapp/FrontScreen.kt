
import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.triviaquizapp.Category
import com.example.triviaquizapp.Difficulty
import com.example.triviaquizapp.QuestionOutput
import com.example.triviaquizapp.Screen
import com.example.triviaquizapp.model.QuestionInput
import com.example.triviaquizapp.viewModel.QuestionViewModel


@Composable
fun FrontPage(navController: NavController, questionViewModel: QuestionViewModel = viewModel()) {
    var questionCount by rememberSaveable { mutableStateOf(questionViewModel.questionCount) }
    var difficulty by rememberSaveable { mutableStateOf(questionViewModel.difficulty) }
    var difficultyLevel by rememberSaveable { mutableStateOf(questionViewModel.difficultyLevel) }
    var selectedCategory by rememberSaveable { mutableStateOf(questionViewModel.selectedCategory) }

    val categories: List<Category> = Category.getAllCategories()

    val questionInputList = questionViewModel.questionListResponse
    if (questionInputList.isNotEmpty()) {
        navController.currentBackStackEntry?.savedStateHandle?.set(
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
                .clickable {
                    questionCount = questionCountManager(questionCount)
                    questionViewModel.questionCount = questionCount // Update ViewModel
                }
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
        Box(
            modifier = Modifier
                .background(Color(android.graphics.Color.parseColor("#1E1E1E")))
                .padding(16.dp)
                .fillMaxWidth()
                .clickable {
                    difficultyLevel = (difficultyLevel + 1) % 4
                    questionViewModel.difficultyLevel = difficultyLevel // Update ViewModel
                }
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
        ) {
            items(categories) { category ->
                Spacer(modifier = Modifier.height(10.dp))
                categoryUI(category = category, onCategorySelected = {
                    selectedCategory = category
                    questionViewModel.selectedCategory = selectedCategory // Update ViewModel
                }, selectedCategory = selectedCategory)
            }
        }
        Box(
            modifier = Modifier
                .background(Color(android.graphics.Color.parseColor("#d7f1f1")))
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    Difficulty.getDifficultyByDifficultyLevel(difficultyLevel)?.let {
                        difficulty = it.apiName
                        questionViewModel.difficulty = difficulty // Update ViewModel
                    }
                    var difficultyApi: String? = difficulty
                    if (difficulty == "") {
                        difficultyApi = null
                    }
                    var categoryApi: Int? = selectedCategory.id
                    if (selectedCategory == Category.ANY_CATEGORY) {
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
fun categoryUI(category: Category, onCategorySelected: (Category) -> Unit, selectedCategory: Category) {
    Box(
        modifier = Modifier
            .background(
                if (category == selectedCategory) Color(
                    android.graphics.Color.parseColor("#FF7F00")
                ) else Color(android.graphics.Color.parseColor("#1E1E1E"))
            )
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onCategorySelected(category) }
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = category.displayName,
            color = Color.White
        )
    }
}

fun questionCountManager(questionCount: Int): Int {
    return if (questionCount == 50) {
        5
    } else {
        questionCount + 5
    }
}

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
    FrontPage(navController)
}
