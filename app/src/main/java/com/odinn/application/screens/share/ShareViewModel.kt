package com.odinn.application.screens.share

import android.net.Uri
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Tasks
import com.odinn.application.data.FeedPostsRepository
import com.odinn.application.data.UsersRepository
import com.odinn.application.models.FeedPost
import com.odinn.application.models.User
import com.odinn.application.screens.common.BaseViewModel

class ShareViewModel(private val feedPostsRepo: FeedPostsRepository,
                     private val usersRepo: UsersRepository, onFailureListener: OnFailureListener) : BaseViewModel(onFailureListener) {

    val user = usersRepo.getUser()

    fun share(user: User, imageUri: Uri?, caption: String) {
        if (imageUri != null) {
            usersRepo.uploadUserImage(user.uid, imageUri).onSuccessTask { downloadUri ->
                Tasks.whenAll(
                        usersRepo.setUserImage(user.uid, downloadUri!!),
                        feedPostsRepo.createFeedPost(user.uid, mkFeedPost(user, caption, downloadUri.toString()))
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
