package com.wearetriple.tripleonboarding.ui.fact

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearetriple.tripleonboarding.database.EntityRepository
import com.wearetriple.tripleonboarding.model.Fact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FactViewModel : ViewModel() {
    private val repository = EntityRepository()
    private val factLiveData = MediatorLiveData<List<Fact>>()
    val facts = factLiveData

    companion object {
        private const val DATABASE_KEY = "facts"
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
        val data = repository.getAllFromTable<Fact>(DATABASE_KEY)
        factLiveData.postValue(data)
    }
}