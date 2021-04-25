package com.odinn.application.screens.editprofile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.net.Uri
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.odinn.application.data.UsersRepository
import com.odinn.application.models.User

class EditProfileViewModel(private val onFailureListener: OnFailureListener, private val usersRepo: UsersRepository) : ViewModel() {
    val user: LiveData<User> = usersRepo.getUser()

    fun uploadAndSetUserPhoto(localImage : Uri): Task<Unit> =
        usersRepo.uploadUserPhoto(localImage).onSuccessTask { downloadUrl ->
            usersRepo.updateUserPhoto(downloadUrl!!)
        }.addOnFailureListener(onFailureListener)

    fun updateEmail(currentEmail: String, newEmail: String, password: String) : Task<Unit> =
        usersRepo.updateEmail(currentEmail = currentEmail,newEmail = newEmail, password = password)
                .addOnFailureListener(onFailureListener)

    fun updateUserProfile(currentUser: User, newUser: User): Task<Unit> =
        usersRepo.updateUserProfile(currentUser = currentUser, newUser = newUser)
                .addOnFailureListener(onFailureListener)

}