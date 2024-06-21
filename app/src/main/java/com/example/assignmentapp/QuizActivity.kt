package com.example.assignmentapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.assignmentapp.model.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val questions = QuestionData().getQuestions()
            QuestionsScreen(
                questions = questions,
                modifier = Modifier.padding(
                    top = 60.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 30.dp
                ),
                context = this
            )

        }
    }
}

//@Composable
//fun QuestionsScreen(modifier: Modifier = Modifier, questions: List<Question>, context: Context) {
//    val selectedAnswers = remember { mutableStateListOf<Int?>() }
//    val timerState = remember { TimerState(600) }
//    for (i in questions.indices) {
//        selectedAnswers.add(null)
//    }
//    val allAnswersSelected = selectedAnswers.none { it == null }
//    LaunchedEffect(timerState.currentTime) {
//        if (timerState.currentTime > 0) {
//            delay(1000) // Delay 1 second
//            timerState.decrementTime()
//        } else {
//            timerState.timerExpired = true
//        }
//    }
//    Column(modifier = Modifier.background(color = Color.White)) {
//        Row(Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp)) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(
//                        brush = Brush.horizontalGradient(
//                            colors = listOf(C1, C2, C3)
//                        ), shape = RoundedCornerShape(10.dp)
//                    )
//            ) {
//                Text(
//                    text = "GK Quiz",
//                    modifier = Modifier.padding(start = 70.dp),
//                    style = MaterialTheme.typography.displayLarge,
//                    fontStyle = FontStyle.Italic,
//                    color = COLOR_2B825E
//                )
//                Spacer(modifier = Modifier.height(30.dp))
//                Text(
//                    text = "Time remaining: ${formatTime(timerState.currentTime)}",
//                    style = MaterialTheme.typography.headlineLarge,
//                    color = COLOR_008cff,
//                    modifier = Modifier.padding(bottom = 16.dp)
//                )
//            }
//        }
//        LazyColumn (modifier = modifier){
//            items(questions){question->
//                var selectedAnswer by remember { mutableStateOf<Int?>(null) }
//                QuestionItem(question = question,
//                    selectedOption = selectedAnswer){selectedOption->
//                    selectedAnswer = selectedOption
//                    selectedAnswers[questions.indexOf(question)] = selectedOption
//                }
//
//            }
//            item {
//                Row(modifier = Modifier.padding(start = 80.dp)) {
//                    Box(
//                        modifier = Modifier
//                            .background(
//                                brush = Brush.horizontalGradient(
//                                    colors = listOf(COLOR_53B2FE, COLOR_065AF3)
//                                ),
//                                shape = RoundedCornerShape(8.dp)
//                            )
//                            .width(200.dp)
//                            .height(50.dp)
//                            .clickable {
//                                if (timerState.timerExpired) {
//                                    Toast
//                                        .makeText(
//                                            context,
//                                            "Time's up! You cannot submit.",
//                                            Toast.LENGTH_SHORT
//                                        )
//                                        .show()
//                                } else if (!allAnswersSelected) {
//                                    Toast
//                                        .makeText(
//                                            context,
//                                            "All answers are mandatory",
//                                            Toast.LENGTH_SHORT
//                                        )
//                                        .show()
//                                } else {
//                                    CheckAnswers(questions, selectedAnswers, context)
//                                }
//
//                            },
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "Submit",
//                            style = MaterialTheme.typography.headlineSmall,
//                            color = COLOR_f6f6f6
//                        )
//                    }
//                }
//
//
//            }
//
//        }
//
//
//
//
//    }
//
//}
//
//class TimerState(initialTime: Int) {
//    var currentTime by mutableStateOf(initialTime)
//    var timerExpired by mutableStateOf(false)
//
//    fun decrementTime() {
//        currentTime--
//    }
//}
//
//@Composable
//fun QuestionItem(question: Question, selectedOption: Int?, onOptionSelected: (Int) -> Unit) {
//
//    Column {
//        Text(text = question.question,
//            style = MaterialTheme.typography.bodyLarge)
//        Column {
//            question.options.forEachIndexed {index,option->
//                Row (verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier.padding(top = 10.dp)){
//                    RadioButton(
//                        selected = selectedOption == index,
//                        onClick = {onOptionSelected(index)}
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(text = option,
//                        modifier = Modifier.padding(start = 8.dp),
//                        style = MaterialTheme.typography.bodyLarge)
//
//                }
//
//            }
//        }
//
//    }
//}
//fun formatTime(seconds: Int): String {
//    val minutes = seconds / 60
//    val remainingSeconds = seconds % 60
//    return "${String.format("%02d", minutes)}:${String.format("%02d", remainingSeconds)}"
//}
//
//fun CheckAnswers(questions: List<Question>, selectedAnswers: List<Int?>, context: Context){
//    var correctCount = 0
//    for (i in questions.indices) {
//        val question = questions[i]
//        val selectedOption = selectedAnswers[i]
//        if (selectedOption != null && selectedOption == question.answer) {
//            correctCount++
//        }
//    }
//
//    // Example: Show a message or navigate to a result screen
//    val totalQuestions = questions.size
//    val incorrectCount = totalQuestions - correctCount
//    val message = "You answered $correctCount out of $totalQuestions questions correctly. $incorrectCount incorrect."
//    CoroutineScope(Dispatchers.Main).launch {
//        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
//    }
//}