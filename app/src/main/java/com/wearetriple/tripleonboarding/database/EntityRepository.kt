package com.wearetriple.tripleonboarding.database

/**
 * Functions as an access point to the database for the application.
 * The base repository decides which database class the app uses to get its data from.
 */
open class EntityRepository {
    companion object {
        val repository = FirebaseRepository()
    }

    /**
     * Gets all data from given table name.
     * @param table the name of the table
     * @return A [List] of data from given [table]
     */
    suspend inline fun <reified E> getAllFromTable(table: String): List<E> {
        return repository.getAllFromTable(table, E::class.java)
    }
}