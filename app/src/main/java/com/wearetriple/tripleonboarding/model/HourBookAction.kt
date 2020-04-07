package com.wearetriple.tripleonboarding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Call to action of a specific hour book topic.
 */
@Parcelize
class HourBookAction(
    val label: String = "",
    val url: String = ""
) : Parcelable