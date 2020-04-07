package com.wearetriple.tripleonboarding.model

/**
 * A set of data about the quiz's question used in the Quiz screen.
 * Each question must have at least two answers.
 * Multiple correct answers can be assigned.
 */
data class QuizQuestion(
    val question: String = "",
    val answer: ArrayList<Answer> = arrayListOf()
)