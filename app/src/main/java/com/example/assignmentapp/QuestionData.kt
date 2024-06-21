package com.example.assignmentapp

import com.example.assignmentapp.model.Question
import kotlinx.serialization.json.Json

class QuestionData {
    val questionData = """
        [
          {
            "question": "What is the capital of France?",
            "options": ["London", "Paris", "Berlin", "Madrid"],
            "answer": 1
          },
          {
            "question": "Who wrote the play 'Romeo and Juliet'?",
            "options": ["William Shakespeare", "Charles Dickens", "Jane Austen", "Mark Twain"],
            "answer": 0
          },
          {
            "question": "What is the chemical symbol for water?",
            "options": ["H2O", "CO2", "NaCl", "O2"],
            "answer": 0
          },
          {
            "question": "Which planet is known as the Red Planet?",
            "options": ["Mars", "Venus", "Jupiter", "Saturn"],
            "answer": 0
          },
          {
            "question": "Who painted the Mona Lisa?",
            "options": ["Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh", "Michelangelo"],
            "answer": 0
          },
          {
            "question": "What is the largest mammal in the world?",
            "options": ["Blue whale", "Elephant", "Giraffe", "Hippopotamus"],
            "answer": 0
          },
          {
            "question": "Who invented the telephone?",
            "options": ["Alexander Graham Bell", "Thomas Edison", "Nikola Tesla", "Albert Einstein"],
            "answer": 0
          },
          {
            "question": "Which country is famous for kangaroos?",
            "options": ["Australia", "Brazil", "Canada", "India"],
            "answer": 0
          },
          {
            "question": "What year did the Titanic sink?",
            "options": ["1912", "1900", "1920", "1930"],
            "answer": 0
          },
          {
            "question": "Who was the first President of the United States?",
            "options": ["George Washington", "Thomas Jefferson", "Abraham Lincoln", "John Adams"],
            "answer": 0
          }
        ]

    """.trimIndent()

    fun getQuestions(): List<Question>{
        return Json.decodeFromString<List<Question>>(questionData)
    }
}