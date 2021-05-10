package com.odinn.application.data

import android.arch.lifecycle.LiveData
import android.net.Uri
import com.google.android.gms.tasks.Task
import com.odinn.application.models.FeedPost
import com.odinn.application.models.User

interface UsersRepository {
    fun getUsers(): LiveData<List<User>>
    fun currentUid(): String?
    fun addFollow(fromUid: String, toUid: String): Task<Unit>
    fun deleteFollow(fromUid: String, toUid: String): Task<Unit>
    fun addFollower(fromUid: String, toUid: String): Task<Unit>
    fun deleteFollower(fromUid: String, toUid: String): Task<Unit>
    fun getUser(): LiveData<User>
    fun uploadUserPhoto(localImage: Uri): Task<Uri>
    fun updateUserPhoto(downloadUrl: Uri?) : Task<Unit>
    fun updateEmail(currentEmail: String, newEmail: String, password: String): Task<Unit>
    fun updateUserProfile(currentUser: User, newUser: User): Task<Unit>
    fun getImages(uid: String): LiveData<List<String>>
    fun isUserExistsForEmail(email: String): Task<Boolean>
    fun createUser(user: User, password: String) : Task<Unit>
    fun uploadUserImage(uid: String, imageUri: Uri): Task<Uri>
    fun setUserImage(uid: String, downloadUrl: Uri): Task<Unit>
    fun getUser(uid: String): LiveData<User>
}