package com.wearetriple.tripleonboarding.ui.department.overview

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.wearetriple.tripleonboarding.database.FirebaseQueryLiveData
import com.wearetriple.tripleonboarding.model.Department
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DepartmentOverviewViewModel : ViewModel() {
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val liveData = FirebaseQueryLiveData(DATABASE_REF)
    private val departmentLiveData = MediatorLiveData<List<Department>>()

    companion object {
        private const val DATABASE_KEY = "department"
        private val DATABASE_REF = FirebaseDatabase.getInstance().getReference(DATABASE_KEY)
    }

    init {
        departmentLiveData.addSource(
            liveData
        ) { dataSnapshot ->
            if (dataSnapshot != null) {
                mainScope.launch {
                    val list = ArrayList<Department>()

                    dataSnapshot.children.forEach {
                        val item: Department? = it.getValue(Department::class.java)

                        if (item != null)
                            list.add(item)
                    }

                    departmentLiveData.postValue(list)
                }
            } else {
                departmentLiveData.setValue(arrayListOf())
            }
        }
    }

    @NonNull
    fun getAll(): LiveData<List<Department>> {
        return departmentLiveData
    }
}