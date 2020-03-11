package com.wearetriple.tripleonboarding.model

/**
 * A set of data about information topics used in the Info screen.
 */
data class InfoTopic(
    var id: Int = 0,
    val title: String = "",
    val content: String = ""
) : Identifiable() {
    companion object {
        const val DATABASE_KEY = "info"
    }
}