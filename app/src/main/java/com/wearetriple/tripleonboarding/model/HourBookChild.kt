package com.wearetriple.tripleonboarding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Child of a specific hour book topic.
 */
@Parcelize
class HourBookChild(
    val code: String = "",
    val content: String = ""
) : Parcelable