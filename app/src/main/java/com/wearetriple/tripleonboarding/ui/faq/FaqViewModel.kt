package com.wearetriple.tripleonboarding.ui.faq

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearetriple.tripleonboarding.database.EntityRepository
import com.wearetriple.tripleonboarding.model.Faq
import kotlinx.coroutines.launch

class FaqViewModel : ViewModel() {
    private val repository = EntityRepository()
    private val faqLiveData = MutableLiveData<List<Faq>>()
    val faq: LiveData<List<Faq>> = faqLiveData

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
    private suspend fun postLiveData() {
        val data = repository.getAllFromTable<Faq>(DATABASE_KEY)
        faqLiveData.postValue(data)
    }
}