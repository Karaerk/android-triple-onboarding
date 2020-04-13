package com.wearetriple.tripleonboarding.ui.hourbook.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wearetriple.tripleonboarding.model.HourBookTopic

class HourBookDetailViewModel : ViewModel() {
    val hourBookTopic = MutableLiveData<HourBookTopic>()

    companion object {
        const val CLICKED_HOUR_BOOK_TOPIC = "CLICKED_HOUR_BOOK_TOPIC"
    }

    /**
     * Checks if the given url is properly formatted as required by Android.
     */
    fun getProperlyFormattedUrl(url: String): String {
        var newUrl = url

        if (!isUrlProperlyFormatted(newUrl))
            newUrl = "http://$url"

        return newUrl
    }

    /**
     * Checks if an action (like opening a website) is present at current topic.
     */
    fun isActionPresent() = hourBookTopic.value?.action != null

    /**
     * Checks if current topic has subjects with its own content.
     */
    fun isChildSubjectsPresent() = hourBookTopic.value!!.child.size > 0

    /**
     * Checks if given url follows Android's pattern for urls.
     */
    private fun isUrlProperlyFormatted(url: String) =
        (url.startsWith("http://") || url.startsWith("https://"))
}