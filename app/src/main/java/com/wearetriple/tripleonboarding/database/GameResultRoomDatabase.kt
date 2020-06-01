package com.wearetriple.tripleonboarding.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wearetriple.tripleonboarding.model.GameResult
import com.wearetriple.tripleonboarding.model.helper.Converts

@Database(entities = [GameResult::class], version = 1, exportSchema = false)
@TypeConverters(Converts::class)
abstract class GameResultRoomDatabase : RoomDatabase() {

    abstract fun gameResultDao(): GameResultDao

    companion object {
        private const val DATABASE_NAME = "GAME_RESULT_DATABASE"

        @Volatile
        private var INSTANCE: GameResultRoomDatabase? = null

        fun getDatabase(context: Context): GameResultRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(GameResultRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            GameResultRoomDatabase::class.java,
                            DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}