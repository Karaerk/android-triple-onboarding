package com.wearetriple.tripleonboarding.model

/**
 * A set of data about frequently asked questions used in the Faq screen.
 */
class Faq(
    val question: String = "",
    val answer: String = ""
) : Identifiable() {
    companion object {
        const val DATABASE_KEY = "faq"
    }
}