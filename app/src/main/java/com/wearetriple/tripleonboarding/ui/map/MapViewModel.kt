package com.wearetriple.tripleonboarding.ui.map

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.wearetriple.tripleonboarding.database.FirebaseQueryLiveData
import com.wearetriple.tripleonboarding.model.MapLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val liveData = FirebaseQueryLiveData(DATABASE_REF)
    private val mapLevelLiveData = MediatorLiveData<List<MapLevel>>()
    val mapLevels = mapLevelLiveData

    companion object {
        private const val DATABASE_KEY = "map"
        private val DATABASE_REF = FirebaseDatabase.getInstance().getReference(DATABASE_KEY)
    }

    init {
        mapLevelLiveData.addSource(
            liveData
        ) { dataSnapshot ->
            if (dataSnapshot != null) {
                mainScope.launch {
                    val list = ArrayList<MapLevel>()

                    dataSnapshot.children.forEach {
                        val item: MapLevel? = it.getValue(MapLevel::class.java)

                        if (item != null)
                            list.add(item)
                    }

                    mapLevelLiveData.postValue(list)
                }
            } else {
                mapLevelLiveData.setValue(arrayListOf())
            }
        }
    }
}