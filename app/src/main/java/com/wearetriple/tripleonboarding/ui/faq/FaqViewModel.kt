package com.wearetriple.tripleonboarding.ui.faq

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.wearetriple.tripleonboarding.database.FirebaseQueryLiveData
import com.wearetriple.tripleonboarding.model.Faq
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FaqViewModel : ViewModel() {
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val liveData: FirebaseQueryLiveData = FirebaseQueryLiveData(DATABASE_REF)
    private val faqLiveData = MediatorLiveData<List<Faq>>()
    val faq = faqLiveData

    companion object {
        private const val DATABASE_KEY = "faq"
        private val DATABASE_REF = FirebaseDatabase.getInstance().getReference(DATABASE_KEY)
    }

    init {
        faqLiveData.addSource(
            liveData
        ) { dataSnapshot ->
            if (dataSnapshot != null) {
                mainScope.launch {
                    val list = ArrayList<Faq>()

                    dataSnapshot.children.forEach {
                        val item: Faq? = it.getValue(Faq::class.java)

                        if (item != null)
                            list.add(item)
                    }

                    faqLiveData.postValue(list)
                }
            } else {
                faqLiveData.setValue(arrayListOf())
            }
        }
    }
}