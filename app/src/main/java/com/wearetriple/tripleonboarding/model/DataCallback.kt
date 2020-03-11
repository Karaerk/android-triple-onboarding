package com.wearetriple.tripleonboarding.model

/**
 * Used to provide callback functions when reading data from the database.
 */
interface DataCallback<E> {
    fun onCallback(list: ArrayList<E>)
}