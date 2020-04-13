package com.wearetriple.tripleonboarding.ui.info

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.wearetriple.tripleonboarding.database.FirebaseQueryLiveData
import com.wearetriple.tripleonboarding.model.InfoTopic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class InfoViewModel : ViewModel() {
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val liveData = FirebaseQueryLiveData(DATABASE_REF)
    private val infoTopicLiveData = MediatorLiveData<List<InfoTopic>>()
    val infoTopics = infoTopicLiveData

    companion object {
        private const val DATABASE_KEY = "info"
        private val DATABASE_REF = FirebaseDatabase.getInstance().getReference(DATABASE_KEY)
    }

    init {
        infoTopicLiveData.addSource(
            liveData
        ) { dataSnapshot ->
            if (dataSnapshot != null) {
                mainScope.launch {
                    val list = ArrayList<InfoTopic>()

                    dataSnapshot.children.forEach {
                        val item: InfoTopic? = it.getValue(InfoTopic::class.java)

                        if (item != null)
                            list.add(item)
                    }

                    infoTopicLiveData.postValue(list)
                }
            } else {
                infoTopicLiveData.setValue(arrayListOf())
            }
        }
    }
}