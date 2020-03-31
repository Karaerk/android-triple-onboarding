package com.wearetriple.tripleonboarding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A set of data about departments used in the Department screen.
 */
@Parcelize
data class Department(
    val title: String = "",
    val content: String = "",
    val thumbnail: String = "",
    val image: String = ""
) : Identifiable(), Parcelable {
    companion object {
        const val DATABASE_KEY = "department"
    }
}