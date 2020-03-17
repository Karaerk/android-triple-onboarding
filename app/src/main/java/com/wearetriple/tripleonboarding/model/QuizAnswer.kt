package com.wearetriple.tripleonboarding.model

const val CORRECT_ANSWER = 1L

/**
 * Answers used for QuizQuestion.
 */
class QuizAnswer(
    val content: String = "",
    val correct: Long = 0L
)