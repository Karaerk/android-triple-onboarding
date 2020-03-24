package com.wearetriple.tripleonboarding.model

/**
 * A set of data about departments used in the Department screen.
 */
data class Department(
    val title: String = "",
    val content: String = "",
    val thumbnail: String = "",
    val image: String = ""
) : Identifiable() {
    companion object {
        const val DATABASE_KEY = "department"
    }
}