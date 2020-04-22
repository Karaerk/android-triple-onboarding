package com.wearetriple.tripleonboarding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A set of data about a video used in the Video screen.
 */
@Parcelize
class Video(
    val title: String = "",
    val thumbnail: String = "",
    val url: String = ""
) : Parcelable