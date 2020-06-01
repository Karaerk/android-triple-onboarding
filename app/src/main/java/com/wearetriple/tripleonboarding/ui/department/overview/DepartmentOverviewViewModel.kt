package com.wearetriple.tripleonboarding.ui.department.overview

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearetriple.tripleonboarding.database.EntityRepository
import com.wearetriple.tripleonboarding.model.Department
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DepartmentOverviewViewModel : ViewModel() {
    private val repository = EntityRepository()
    private val departmentLiveData = MediatorLiveData<List<Department>>()
    val departments = departmentLiveData

    companion object {
        private const val DATABASE_KEY = "department"
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
        val data = repository.getAllFromTable<Department>(DATABASE_KEY)
        departmentLiveData.postValue(data)
    }
}