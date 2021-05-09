package com.odinn.application.screens.comments

import android.arch.lifecycle.LiveData
import com.google.android.gms.tasks.OnFailureListener
import com.odinn.application.data.FeedPostsRepository
import com.odinn.application.data.UsersRepository
import com.odinn.application.models.Comment
import com.odinn.application.models.User
import com.odinn.application.screens.common.BaseViewModel

class CommentsViewModel(private val feedPostsRepo: FeedPostsRepository,
                        usersRepo: UsersRepository,
                        onFailureListener: OnFailureListener) : BaseViewModel(onFailureListener) {
    lateinit var comments: LiveData<List<Comment>>
    private lateinit var postId: String
    val user: LiveData<User> = usersRepo.getUser()

    fun init(postId: String) {
        this.postId = postId
        comments = feedPostsRepo.getComments(postId)
    }

    fun createComment(text: String, user: User) {
        val comment = Comment(
                uid = user.uid,
                username = user.username,
                photo = user.photo,
                text = text
        )
        feedPostsRepo.createComment(postId, comment).addOnFailureListener(onFailureListener)
    }
}