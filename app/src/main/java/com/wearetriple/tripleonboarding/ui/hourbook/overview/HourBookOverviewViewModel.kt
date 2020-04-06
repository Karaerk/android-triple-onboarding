package com.wearetriple.tripleonboarding.ui.hourbook.overview

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.wearetriple.tripleonboarding.database.FirebaseQueryLiveData
import com.wearetriple.tripleonboarding.model.HourBookTopic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HourBookOverviewViewModel : ViewModel() {
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val liveData = FirebaseQueryLiveData(DATABASE_REF)
    private val hourBookTopicsLiveData = MediatorLiveData<List<HourBookTopic>>()

    companion object {
        private const val DATABASE_KEY = "hours"
        private val DATABASE_REF = FirebaseDatabase.getInstance().getReference(DATABASE_KEY)
    }

    init {
        hourBookTopicsLiveData.addSource(
            liveData
        ) { dataSnapshot ->
            if (dataSnapshot != null) {
                mainScope.launch {
                    val list = ArrayList<HourBookTopic>()

                    dataSnapshot.children.forEach {
                        val item: HourBookTopic? = it.getValue(HourBookTopic::class.java)

                        if (item != null)
                            list.add(item)
                    }

                    hourBookTopicsLiveData.postValue(list)
                }
            } else {
                hourBookTopicsLiveData.setValue(arrayListOf())
            }
        }
    }

    @NonNull
    fun getAll(): LiveData<List<HourBookTopic>> {
        return hourBookTopicsLiveData
    }
}