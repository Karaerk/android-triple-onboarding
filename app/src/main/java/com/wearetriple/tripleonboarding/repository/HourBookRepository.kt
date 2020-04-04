package com.wearetriple.tripleonboarding.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.wearetriple.tripleonboarding.model.DataCallback
import com.wearetriple.tripleonboarding.model.HourBookTopic
import com.wearetriple.tripleonboarding.model.Identifiable
import com.wearetriple.tripleonboarding.model.UNSET_ID

open class HourBookRepository {
    /**
     * Gets all keys and values from a specific entity.
     */
    fun getAll(dataCallback: DataCallback<HourBookTopic>) {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference(HourBookTopic.DATABASE_KEY)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = ArrayList<HourBookTopic>()

                dataSnapshot.children.forEach {
                    val item: HourBookTopic? = it.getValue(HourBookTopic::class.java)

                    if (item != null) {
                        item.key = it.key?.toInt() ?: UNSET_ID
                        list.add(item)
                    }
                }

                dataCallback.onCallback(list)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(HourBookTopic::class.java.simpleName, "getAll:onCancelled", databaseError.toException())
            }
        })
    }
}