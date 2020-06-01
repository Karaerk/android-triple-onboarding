package com.wearetriple.tripleonboarding.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.wearetriple.tripleonboarding.model.Game
import com.wearetriple.tripleonboarding.model.GameResult

@Dao
interface GameResultDao {

    @Insert
    suspend fun insertGameResult(gameResult: GameResult): Long

    @Update
    suspend fun updateGameResult(gameResult: GameResult)

    @Query("SELECT * FROM GameResult WHERE game = :game LIMIT 1")
    suspend fun getHighscoreOfGame(game: Game): GameResult?
}