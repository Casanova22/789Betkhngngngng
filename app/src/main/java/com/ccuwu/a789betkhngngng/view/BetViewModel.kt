package com.ccuwu.a789betkhngngng.view

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class BetViewModel : ViewModel() {

    private val fsDatabase = FirebaseFirestore.getInstance()
    private val fsData = MutableLiveData<List<DocumentSnapshot>>()

    init {
        readFireData()
    }


    fun readFireData(): LiveData<List<DocumentSnapshot>> {
        fsDatabase.collection("06June").document("789BET")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    fsData.value = listOf(snapshot)
                } else {
                    Log.d(ContentValues.TAG, "No data found")
                }
            }
        return fsData
    }
}