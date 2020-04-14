package com.wearetriple.tripleonboarding.ui.faq

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.wearetriple.tripleonboarding.database.FirebaseQueryLiveData
import com.wearetriple.tripleonboarding.model.Faq
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FaqViewModel : ViewModel() {
    private val liveData: FirebaseQueryLiveData = FirebaseQueryLiveData(DATABASE_REF)
    private val faqLiveData = MediatorLiveData<List<Faq>>()
    val faq = faqLiveData

    companion object {
        private const val DATABASE_KEY = "faq"
        private val DATABASE_REF = FirebaseDatabase.getInstance().getReference(DATABASE_KEY)
    }

    init {
        faqLiveData.addSource(liveData) { dataSnapshot ->
            if (dataSnapshot != null) {
                viewModelScope.launch {
                    postLiveData(dataSnapshot)
                }
            } else {
                faqLiveData.setValue(arrayListOf())
            }
        }
    }

    /**
     * Posts a new set of data inside the live data attribute.
     */
    private suspend fun postLiveData(dataSnapshot: DataSnapshot) = withContext(Dispatchers.IO) {
        val list = ArrayList<Faq>()

        dataSnapshot.children.forEach {
            val item: Faq? = it.getValue(Faq::class.java)

            if (item != null)
                list.add(item)
        }

        faqLiveData.postValue(list)
    }
}