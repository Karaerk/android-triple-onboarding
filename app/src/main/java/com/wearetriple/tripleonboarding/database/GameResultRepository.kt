package com.wearetriple.tripleonboarding.database

import android.content.Context
import com.wearetriple.tripleonboarding.model.Game
import com.wearetriple.tripleonboarding.model.GameResult

class GameResultRepository(context: Context) {

    private val gameResultDao: GameResultDao

    init {
        val database =
            GameResultRoomDatabase.getDatabase(
                context
            )
        gameResultDao = database!!.gameResultDao()
    }

    suspend fun getHighscoreOfGame(game: Game): GameResult? =
        gameResultDao.getHighscoreOfGame(game)

    suspend fun insertGameResult(gameResult: GameResult): Long =
        gameResultDao.insertGameResult(gameResult)

    suspend fun updateGameResult(gameResult: GameResult) =
        gameResultDao.updateGameResult(gameResult)
}