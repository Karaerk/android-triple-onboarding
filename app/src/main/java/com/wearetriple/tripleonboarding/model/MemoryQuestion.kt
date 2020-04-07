package com.wearetriple.tripleonboarding.model

/**
 * A set of data about the memory's question used in the Memory screen.
 * Each question must have at least two answers.
 * Multiple correct answers can be assigned.
 */
data class MemoryQuestion(
    val image: String = "",
    val answer: ArrayList<Answer> = arrayListOf()
)