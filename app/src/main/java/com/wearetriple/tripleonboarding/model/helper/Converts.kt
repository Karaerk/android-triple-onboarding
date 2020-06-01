package com.wearetriple.tripleonboarding.model.helper

import androidx.room.TypeConverter
import com.wearetriple.tripleonboarding.model.Game

class Converts {
    @TypeConverter
    fun fromGameEnum(value: Int): Game {
        return value.let { Game.values()[it] }
    }

    @TypeConverter
    fun gameEnumToInt(game: Game): Int {
        return game.ordinal
    }
}