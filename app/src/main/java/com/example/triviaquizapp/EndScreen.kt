import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.triviaquizapp.Screen
import androidx.compose.ui.platform.LocalContext

@Composable
fun EndScreen(questionCount: Int, score: Int, correctQuestion: Int, navController: NavController) {
    val percentageScore = (score.toDouble() / (questionCount * 10)) * 100
    val greeting: String = "Congratulations!"
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .background(Color(0xFFd7f1f1))
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .border(3.dp, Color(0xFFFF7F00)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = greeting,
                        color = Color.Black,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "You scored $percentageScore%",
                        color = Color(0xFFFF7F00),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Quiz completed successfully.",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "You attempted $questionCount questions and $correctQuestion were correct.",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier.background(Color(0xFFd7f1f1))
                    .fillMaxWidth()
                    .height(40.dp)
                    .border(2.dp, Color(0xFFFF7F00))
                    .padding(10.dp)
                    .clickable { navController.navigate(Screen.Home.route) },
                contentAlignment = Alignment.Center

            ) {
                Text(
                    text = "PLAY AGAIN",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold

                )
            }

           Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier.background(Color(0xFFd7f1f1))
                    .fillMaxWidth()
                    .height(40.dp)
                    .border(2.dp, Color(0xFFFF7F00))
                    .padding(10.dp)
                    .clickable {
                        (context as? Activity)?.finish()
                    },
                contentAlignment = Alignment.Center

            ) {
                Text(
                    text = "EXIT",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold

                )
            }

        }
    }

}

@Preview
@Composable
fun EndScreenPreview() {
    val navController = rememberNavController()
    EndScreen(10, 90, 9, navController)
}
