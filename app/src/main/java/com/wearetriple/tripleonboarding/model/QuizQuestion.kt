package com.wearetriple.tripleonboarding.model

/**
 * A set of data about the quiz's question used in the Quiz screen.
 * Each quiz must have at least two answers.
 * Multiple correct answers can be assigned.
 */
class QuizQuestion(
    val question: String = "",
    val answer: ArrayList<QuizAnswer> = arrayListOf()
) : Identifiable() {
    companion object {
        const val DATABASE_KEY = "quiz"
    }
}