package com.wearetriple.tripleonboarding.ui.hourbook.overview

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.wearetriple.tripleonboarding.database.FirebaseQueryLiveData
import com.wearetriple.tripleonboarding.model.HourBookTopic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HourBookOverviewViewModel : ViewModel() {
    private val liveData = FirebaseQueryLiveData(DATABASE_REF)
    private val hourBookTopicsLiveData = MediatorLiveData<List<HourBookTopic>>()
    val hourBookTopics = hourBookTopicsLiveData

    companion object {
        private const val DATABASE_KEY = "hours"
        private val DATABASE_REF = FirebaseDatabase.getInstance().getReference(DATABASE_KEY)
    }

    init {
        hourBookTopicsLiveData.addSource(liveData) { dataSnapshot ->
            if (dataSnapshot != null) {
                viewModelScope.launch {
                    postLiveData(dataSnapshot)
                }
            } else {
                hourBookTopicsLiveData.setValue(arrayListOf())
            }
        }
    }

    /**
     * Posts a new set of data inside the live data attribute.
     */
    private suspend fun postLiveData(dataSnapshot: DataSnapshot) = withContext(Dispatchers.IO) {
        val list = ArrayList<HourBookTopic>()

        dataSnapshot.children.forEach {
            val item: HourBookTopic? = it.getValue(HourBookTopic::class.java)

            if (item != null)
                list.add(item)
        }

        hourBookTopicsLiveData.postValue(list)
    }
}