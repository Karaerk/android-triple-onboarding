package com.wearetriple.tripleonboarding.database

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Gets its data from a Firebase realtime database.
 * This repository allows to define callback wrappers which are protected from
 * accidental multiple calls to resume/resumeWithException
 */
class FirebaseRepository {

    companion object {
        const val LOG_TAG = "FirebaseRepository"
        const val MISSING_DATA = "data missing"
        const val INVALID_FORMAT = "invalid data format"
    }

    inner class WrappedContinuation<T>(val c: Continuation<T>) : Continuation<T> {
        var isResolved = false
        override val context: CoroutineContext
            get() = c.context

        override fun resumeWith(result: Result<T>) {
            if (!isResolved) {
                isResolved = true
                c.resumeWith(result)
            }
        }

    }

    private suspend inline fun <T> suspendCoroutineWrapper(crossinline block: (WrappedContinuation<T>) -> Unit): T =
        suspendCancellableCoroutine { c ->
            val wd = WrappedContinuation(c)
            block(wd)
        }

    suspend fun <T> getAllFromTable(table: String, dataType: Class<T>): List<T> =
        suspendCoroutineWrapper { d ->
            val ref = FirebaseDatabase.getInstance().getReference(table)

            ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    d.resumeWithException(p0.toException())
                    Log.e(LOG_TAG, "Error while getting data from $table", p0.toException())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        val data = ArrayList<T>()

                        snapshot.children.forEach {
                            val item: T? = it.getValue(dataType)

                            if (item != null)
                                data.add(item)
                        }

                        if (data.isNotEmpty()) {
                            d.resume(data)
                        } else {
                            val errorMessage = when(snapshot.value) {
                                null -> MISSING_DATA
                                else -> INVALID_FORMAT
                            }
                            d.resumeWithException(Exception(errorMessage))
                        }
                    } catch (e: Exception) {
                        d.resumeWithException(e)
                        Log.e(LOG_TAG, e.message, e)
                    }
                }
            })
        }

}