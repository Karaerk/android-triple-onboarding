package com.wearetriple.tripleonboarding.ui.fact

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.wearetriple.tripleonboarding.database.FirebaseQueryLiveData
import com.wearetriple.tripleonboarding.model.Fact
import kotlinx.coroutines.launch

class FactViewModel : ViewModel() {
    private val liveData = FirebaseQueryLiveData(DATABASE_REF)
    private val factLiveData = MediatorLiveData<List<Fact>>()
    val facts = factLiveData

    companion object {
        private const val DATABASE_KEY = "facts"
        private val DATABASE_REF = FirebaseDatabase.getInstance().getReference(DATABASE_KEY)
    }

    init {
        factLiveData.addSource(liveData) { dataSnapshot ->
            if (dataSnapshot != null) {
                viewModelScope.launch {
                    postLiveData(dataSnapshot)
                }
            } else {
                factLiveData.value = arrayListOf()
            }
        }
    }

    /**
     * Posts a new set of data inside the live data attribute.
     */
    private fun postLiveData(dataSnapshot: DataSnapshot) {

        val list = ArrayList<Fact>()

        dataSnapshot.children.forEach {
            val item: Fact? = it.getValue(Fact::class.java)

            if (item != null)
                list.add(item)
        }

        factLiveData.postValue(list)
    }
}