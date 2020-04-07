package com.wearetriple.tripleonboarding.model

/**
 * Keeps track of an active game's status
 */
data class GameStatus(
    var currentCorrectAnswers: Int = 0,
    var currentWrongAnswers: Int = 0,
    var totalScore: Int = 0
)