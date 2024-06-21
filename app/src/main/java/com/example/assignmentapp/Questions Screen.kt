package com.example.assignmentapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.assignmentapp.model.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun QuestionsScreen(modifier: Modifier = Modifier, questions: List<Question>, context: Context) {
    val selectedAnswers = remember { mutableStateListOf<Int?>() }
    val timerState = remember { TimerState(600) }
    val submissionState = remember { mutableStateOf(false) }
    val resultMessage = remember { mutableStateOf("") }
    if (selectedAnswers.isEmpty()) {
        for (i in questions.indices) {
            selectedAnswers.add(null)
        }
    }

    val allAnswersSelected = selectedAnswers.none { it == null }

    LaunchedEffect(timerState.currentTime) {
        if (timerState.currentTime > 0) {
            delay(1000) // Delay 1 second
            timerState.decrementTime()
        } else {
            timerState.timerExpired = true
        }
    }

    Column(modifier = Modifier.background(color = Color.White)) {
        Row(Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(C1, C2, C3)
                        ), shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Text(
                    text = "GK Quiz",
                    modifier = Modifier.padding(start = 70.dp),
                    style = MaterialTheme.typography.displayLarge,
                    fontStyle = FontStyle.Italic,
                    color = COLOR_2B825E
                )


            }
        }
        if (submissionState.value) {
            Text(
                text = resultMessage.value,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Time remaining: ${formatTime(timerState.currentTime)}",
                style = MaterialTheme.typography.headlineLarge,
                color = COLOR_008cff,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            LazyColumn(modifier = modifier) {
                itemsIndexed(questions) { index, question ->
                    QuestionItem(
                        question = question,
                        selectedOption = selectedAnswers[index]
                    ) { selectedOption ->
                        selectedAnswers[index] = selectedOption
                    }
                }
                item {
                    Row(modifier = Modifier.padding(start = 80.dp)) {
                        Box(
                            modifier = Modifier
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(COLOR_53B2FE, COLOR_065AF3)
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .width(200.dp)
                                .height(50.dp)
                                .clickable {
                                    if (timerState.timerExpired) {
                                        Toast
                                            .makeText(
                                                context,
                                                "Time's up! You cannot submit.",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    } else if (!allAnswersSelected) {
                                        Toast
                                            .makeText(
                                                context,
                                                "All answers are mandatory",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    } else {
                                        val result = CheckAnswers(questions, selectedAnswers)
                                        resultMessage.value = result
                                        submissionState.value = true
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Submit",
                                style = MaterialTheme.typography.headlineSmall,
                                color = COLOR_f6f6f6
                            )
                        }
                    }
                }
            }
        }
    }
}

class TimerState(initialTime: Int) {
    var currentTime by mutableStateOf(initialTime)
    var timerExpired by mutableStateOf(false)

    fun decrementTime() {
        currentTime--
    }
}

@Composable
fun QuestionItem(question: Question, selectedOption: Int?, onOptionSelected: (Int) -> Unit) {
    Column {
        Text(
            text = question.question,
            style = MaterialTheme.typography.bodyLarge
        )
        Column {
            question.options.forEachIndexed { index, option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp).clickable { onOptionSelected(index) }
                ) {
                    RadioButton(
                        selected = selectedOption == index,
                        onClick = { onOptionSelected(index) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = option,
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "${String.format("%02d", minutes)}:${String.format("%02d", remainingSeconds)}"
}

fun CheckAnswers(questions: List<Question>, selectedAnswers: List<Int?>): String {
    var correctCount = 0
    for (i in questions.indices) {
        val question = questions[i]
        val selectedOption = selectedAnswers[i]
        if (selectedOption != null && selectedOption == question.answer) {
            correctCount++
        }
    }

    val totalQuestions = questions.size
    val incorrectCount = totalQuestions - correctCount
    return "You Scored $correctCount / $totalQuestions"
}
