package com.wearetriple.tripleonboarding.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.wearetriple.tripleonboarding.model.DataCallback
import com.wearetriple.tripleonboarding.model.Identifiable
import com.wearetriple.tripleonboarding.model.UNSET_ID

open class BaseEntityRepository<E : Identifiable>(var databaseKey: String) {
    /**
     * Gets all keys and values from a specific entity.
     */
    inline fun <reified E : Identifiable> getAll(dataCallback: DataCallback<E>) {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference(databaseKey)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = ArrayList<E>()

                dataSnapshot.children.forEach {
                    val item: E? = it.getValue(E::class.java)

                    if (item != null) {
                        item.key = it.key?.toInt() ?: UNSET_ID
                        list.add(item)
                    }
                }

                dataCallback.onCallback(list)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(E::class.java.simpleName, "getAll:onCancelled", databaseError.toException())
            }
        })
    }
}