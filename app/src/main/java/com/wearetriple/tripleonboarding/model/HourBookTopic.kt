package com.wearetriple.tripleonboarding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 * A set of data about hours booking topics used in the Hours book screen.
 */
@Parcelize
data class HourBookTopic(
    var id: Int = 0,
    val title: String = "",
    val content: String = "",
    val action: @RawValue HourBookAction? = null,
    val child: @RawValue ArrayList<HourBookChild> = arrayListOf()
) : Identifiable(), Parcelable {
    companion object {
        const val DATABASE_KEY = "hours"
    }
}