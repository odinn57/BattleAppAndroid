package com.odinn.application.screens

import android.arch.lifecycle.ViewModel
import android.net.Uri
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Tasks
import com.odinn.application.data.UsersRepository
import com.odinn.application.models.FeedPost
import com.odinn.application.models.User

class ShareViewModel(private val usersRepo: UsersRepository, private val onFailureListener: OnFailureListener) : ViewModel() {

    val user = usersRepo.getUser()

    fun share(user: User, imageUri: Uri?, caption: String) {
        if (imageUri != null) {
            usersRepo.uploadUserImage(user.uid, imageUri).onSuccessTask { downloadUri ->
                Tasks.whenAll(
                        usersRepo.setUserImage(user.uid, downloadUri!!),
                        usersRepo.createFeedPost(user.uid, mkFeedPost(user, caption, downloadUri.toString()))
                ).addOnFailureListener(onFailureListener)
            }
        }
    }

    private fun mkFeedPost(user: User, caption: String, imageDownloadUrl: String): FeedPost {
        return FeedPost(
                uid = user.uid,
                username = user.username,
                image = imageDownloadUrl,
                caption = caption,
                photo = user.photo
        )
    }
}
