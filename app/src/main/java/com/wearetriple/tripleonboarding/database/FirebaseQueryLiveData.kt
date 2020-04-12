package com.wearetriple.tripleonboarding.database

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.*

class FirebaseQueryLiveData(ref: DatabaseReference) : LiveData<DataSnapshot>() {
    private var listenerRemovePending: Boolean = false
    private val handler: Handler = Handler()
    private val removeListener: Runnable = ListenerRunnable()
    private val listener: FirebaseValueEventListener = FirebaseValueEventListener()
    private var query: Query = ref

    companion object {
        const val LOG_TAG = "FirebaseQueryLiveData"
        const val POST_DELAY = 2000L
    }

    /**
     * Removes unused listeners only when this class is not being
     * active after POST_DELAY seconds.
     * The aim of this way of working is to prevent unnecessary query execution when user,
     * for example, changes device orientation
     */
    override fun onInactive() {
        handler.postDelayed(removeListener, POST_DELAY)
        listenerRemovePending = true
    }

    /**
     * Sets up the class in a proper way.
     */
    override fun onActive() {
        if (listenerRemovePending)
            handler.removeCallbacks(removeListener)
        else
            query.addValueEventListener(listener)

        listenerRemovePending = false
    }

    /**
     * Listens to the data returned by Firebase.
     */
    inner class FirebaseValueEventListener : ValueEventListener {
        override fun onCancelled(databaseError: DatabaseError) {
            Log.e(LOG_TAG, "Error while listening to query $query", databaseError.toException())
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            value = dataSnapshot
        }
    }

    /**
     * Allows the class to be inactive in a proper way by removing unneeded event listeners.
     */
    open inner class ListenerRunnable : Runnable {
        override fun run() {
            query.removeEventListener(listener)
            listenerRemovePending = false
        }
    }
}