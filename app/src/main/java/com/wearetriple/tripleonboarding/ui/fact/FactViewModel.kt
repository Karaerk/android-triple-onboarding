package com.wearetriple.tripleonboarding.ui.fact

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.wearetriple.tripleonboarding.database.FirebaseQueryLiveData
import com.wearetriple.tripleonboarding.model.Fact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FactViewModel : ViewModel() {
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val liveData = FirebaseQueryLiveData(DATABASE_REF)
    private val factLiveData = MediatorLiveData<List<Fact>>()
    val facts = factLiveData

    companion object {
        private const val DATABASE_KEY = "facts"
        private val DATABASE_REF = FirebaseDatabase.getInstance().getReference(DATABASE_KEY)
    }

    init {
        factLiveData.addSource(
            liveData
        ) { dataSnapshot ->
            if (dataSnapshot != null) {
                mainScope.launch {
                    val list = ArrayList<Fact>()

                    dataSnapshot.children.forEach {
                        val item: Fact? = it.getValue(Fact::class.java)

                        if (item != null)
                            list.add(item)
                    }

                    factLiveData.postValue(list)
                }
            } else {
                factLiveData.setValue(arrayListOf())
            }
        }
    }
}