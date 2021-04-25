package com.odinn.application.data.firebase.common

import android.arch.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.odinn.application.common.ValueEventListenerAdapter

class FirebaseLiveData(private val reference : DatabaseReference) : LiveData<DataSnapshot>() {
    private  val listener = ValueEventListenerAdapter {
        value = it
    }

    override fun onActive() {       //когда подсоединен хотябы один observe
        super.onActive()
        reference.addValueEventListener(listener)
    }

    override fun onInactive() {     //когда убирается последний observe
        super.onInactive()
        reference.removeEventListener(listener)
    }
}