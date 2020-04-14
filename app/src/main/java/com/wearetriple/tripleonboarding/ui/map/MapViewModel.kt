package com.wearetriple.tripleonboarding.ui.map

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.wearetriple.tripleonboarding.database.FirebaseQueryLiveData
import com.wearetriple.tripleonboarding.model.MapLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapViewModel : ViewModel() {
    private val liveData = FirebaseQueryLiveData(DATABASE_REF)
    private val mapLevelLiveData = MediatorLiveData<List<MapLevel>>()
    val mapLevels = mapLevelLiveData

    companion object {
        private const val DATABASE_KEY = "map"
        private val DATABASE_REF = FirebaseDatabase.getInstance().getReference(DATABASE_KEY)
    }

    init {
        mapLevelLiveData.addSource(liveData) { dataSnapshot ->
            if (dataSnapshot != null) {
                viewModelScope.launch {
                    postLiveData(dataSnapshot)
                }
            } else {
                mapLevelLiveData.setValue(arrayListOf())
            }
        }
    }

    /**
     * Posts a new set of data inside the live data attribute.
     */
    private suspend fun postLiveData(dataSnapshot: DataSnapshot) = withContext(Dispatchers.IO) {
        val list = ArrayList<MapLevel>()

        dataSnapshot.children.forEach {
            val item: MapLevel? = it.getValue(MapLevel::class.java)

            if (item != null)
                list.add(item)
        }

        mapLevelLiveData.postValue(list)
    }
}