package com.wearetriple.tripleonboarding.ui.fact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearetriple.tripleonboarding.database.EntityRepository
import com.wearetriple.tripleonboarding.model.Fact
import kotlinx.coroutines.launch

class FactViewModel : ViewModel() {
    private val repository = EntityRepository()
    private val factLiveData = MutableLiveData<List<Fact>>()
    val facts: LiveData<List<Fact>> = factLiveData

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
    private suspend fun postLiveData() {
        val data = repository.getAllFromTable<Fact>(DATABASE_KEY)
        factLiveData.postValue(data)
    }
}