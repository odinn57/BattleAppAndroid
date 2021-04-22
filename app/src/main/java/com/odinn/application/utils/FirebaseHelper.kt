package com.odinn.application.utils

import android.app.Activity
import android.net.Uri
import com.odinn.application.activities.showToast
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class FirebaseHelper(private val activity: Activity) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    val storage: StorageReference = FirebaseStorage.getInstance().reference


    fun currentUserReference(): DatabaseReference =
            database.child("users").child(currentUid()!!)

    fun currentUserImagesReference(): DatabaseReference =
            database.child("images").child(currentUid()!!)

    fun currentUid(): String? =
            auth.currentUser?.uid

}