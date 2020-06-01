package com.wearetriple.tripleonboarding.ui.faq

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearetriple.tripleonboarding.database.EntityRepository
import com.wearetriple.tripleonboarding.model.Faq
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FaqViewModel : ViewModel() {
    private val repository = EntityRepository()
    private val faqLiveData = MediatorLiveData<List<Faq>>()
    val faq = faqLiveData

    companion object {
        private const val DATABASE_KEY = "faq"
    }

    init {
        viewModelScope.launch {
            postLiveData()
        }
    }

    /**
     * Posts a new set of data inside the live data attribute.
     */
    private suspend fun postLiveData() = withContext(Dispatchers.IO) {
        val data = repository.getAllFromTable<Faq>(DATABASE_KEY)
        faqLiveData.postValue(data)
    }
}