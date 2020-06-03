package com.wearetriple.tripleonboarding.model.helper

import androidx.room.TypeConverter
import com.wearetriple.tripleonboarding.model.Game

class Converts {
    @TypeConverter
    fun fromGameEnum(value: String): Game {
        return when(value) {
            "Memory" -> Game.MEMORY
            "Quiz" -> Game.QUIZ
            else -> Game.QUIZ
        }
    }

    @TypeConverter
    fun gameEnumToString(game: Game): String {
        return when(game) {
            Game.MEMORY -> "Memory"
            Game.QUIZ -> "Quiz"
        }
    }
}