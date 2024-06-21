package com.example.assignmentapp.model

import kotlinx.serialization.Serializable


@Serializable
data class Question(
    val question: String,
    val options: List<String>,
    val answer: Int
)