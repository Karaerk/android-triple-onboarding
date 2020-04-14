package com.wearetriple.tripleonboarding.ui.info

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.wearetriple.tripleonboarding.database.FirebaseQueryLiveData
import com.wearetriple.tripleonboarding.model.InfoTopic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class InfoViewModel : ViewModel() {
    private val liveData = FirebaseQueryLiveData(DATABASE_REF)
    private val infoTopicLiveData = MediatorLiveData<List<InfoTopic>>()
    val infoTopics = infoTopicLiveData

    companion object {
        private const val DATABASE_KEY = "info"
        private val DATABASE_REF = FirebaseDatabase.getInstance().getReference(DATABASE_KEY)
    }

    init {
        infoTopicLiveData.addSource(liveData) { dataSnapshot ->
            if (dataSnapshot != null) {
                viewModelScope.launch {
                    postLiveData(dataSnapshot)
                }
            } else {
                infoTopicLiveData.value = arrayListOf()
            }
        }
    }

    /**
     * Posts a new set of data inside the live data attribute.
     */
    private suspend fun postLiveData(dataSnapshot: DataSnapshot) = withContext(Dispatchers.IO) {
        val list = ArrayList<InfoTopic>()

        dataSnapshot.children.forEach {
            val item = it.getValue(InfoTopic::class.java)

            if (item != null)
                list.add(item)
        }

        infoTopicLiveData.postValue(list)
    }
}