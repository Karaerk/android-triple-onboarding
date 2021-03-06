package com.wearetriple.tripleonboarding.database

import android.content.Context
import com.wearetriple.tripleonboarding.model.Game
import com.wearetriple.tripleonboarding.model.GameResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Functions as an access point to the database for the application.
 * The base repository decides which database class the app uses to get its data from.
 */
open class EntityRepository(val context: Context? = null) {

    val remoteRepository = FirebaseRepository()
    private lateinit var localRepository: GameResultRepository

    init {
        if (context != null)
            localRepository = GameResultRepository(context)
    }

    /**
     * Gets all data from given table name.
     * @param table the name of the table
     * @return A [List] of data from given [table]
     */
    suspend inline fun <reified E> getAllFromTable(table: String): List<E> =
        withContext(Dispatchers.IO) {
            return@withContext remoteRepository.getAllFromTable(table, E::class.java)
        }

    /**
     * Returns the user's highscore in given game.
     */
    suspend fun getHighscoreOfGame(game: Game): GameResult? = withContext(Dispatchers.IO) {
        return@withContext localRepository.getHighscoreOfGame(game)
    }

    /**
     * Updates the user's highscore in a game.
     */
    suspend fun updateHighscore(gameResult: GameResult) = withContext(Dispatchers.IO) {
        localRepository.updateGameResult(gameResult)
    }

    /**
     * Inserts the user's highscore in a game.
     */
    suspend fun insertHighscore(gameResult: GameResult): Long = withContext(Dispatchers.IO) {
        return@withContext localRepository.insertGameResult(gameResult)
    }
}