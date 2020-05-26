package com.wearetriple.tripleonboarding.ui.more

import android.app.Application
import androidx.lifecycle.*


class MoreViewModel() : ViewModel() {
    private val menuItems = MediatorLiveData<List<String>>()
    val items = menuItems

    init {
        initMenuItems()
    }

    /**
     * Populates a list of menu items which aren't shown on the bottom navigation of the app
     */
    private fun initMenuItems() {
        menuItems.value = listOf(
            "FAQ",
            "Afdelingen",
            "Urenboek",
            "Video's"
        )
    }
}