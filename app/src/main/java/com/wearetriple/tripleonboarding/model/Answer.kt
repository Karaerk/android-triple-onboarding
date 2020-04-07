package com.wearetriple.tripleonboarding.model

const val CORRECT_ANSWER = 1L

/**
 * Answers used for QuizQuestion and MemoryQuestion.
 */
data class Answer(
    val content: String = "",
    val correct: Long = 0L
)