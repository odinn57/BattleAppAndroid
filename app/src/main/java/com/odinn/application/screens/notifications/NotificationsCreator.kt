package com.odinn.application.screens.notifications

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import android.util.Log
import com.odinn.application.common.Event
import com.odinn.application.common.EventBus
import com.odinn.application.data.FeedPostsRepository
import com.odinn.application.data.NotificationsRepository
import com.odinn.application.data.UsersRepository
import com.odinn.application.data.common.observeFirstNotNull
import com.odinn.application.data.common.zip
import com.odinn.application.models.Notification
import com.odinn.application.models.NotificationType

class NotificationsCreator(private val notificationsRepo: NotificationsRepository,
                           private val usersRepo: UsersRepository,
                           private val feedPostsRepo: FeedPostsRepository) : LifecycleOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)

    init {
        lifecycleRegistry.markState(Lifecycle.State.CREATED);
        lifecycleRegistry.markState(Lifecycle.State.STARTED);

        EventBus.events.observe(this, Observer {
            it?.let { event ->
                when (event) {
                    is Event.CreateFollow -> {
                        getUser(event.fromUid).observeFirstNotNull(this) { user ->
                            val notification = Notification(
                                    uid = user.uid,
                                    username = user.username,
                                    photo = user.photo,
                                    type = NotificationType.Follow)
                            notificationsRepo.createNotification(event.toUid, notification)
                                    .addOnFailureListener {
                                        Log.d(TAG, "Failed to create notification", it)
                                    }
                        }
                    }
                    is Event.CreateLike -> {
                        val userData = usersRepo.getUser(event.uid)
                        val postData = feedPostsRepo.getFeedPost(uid = event.uid, postId = event.postId)

                        userData.zip(postData).observeFirstNotNull(this) { (user, post) ->
                            val notification = Notification(
                                    uid = user.uid,
                                    username = user.username,
                                    photo = user.photo,
                                    postId = post.id,
                                    postImage = post.image,
                                    type = NotificationType.Like)
                            notificationsRepo.createNotification(post.uid, notification)
                                    .addOnFailureListener {
                                        Log.d(TAG, "Failed to create notification", it)
                                    }
                        }
                    }
                    is Event.CreateComment -> {
                        feedPostsRepo.getFeedPost(uid = event.comment.uid, postId = event.postId)
                                .observeFirstNotNull(this) { post ->
                                    val notification = Notification(
                                            uid = event.comment.uid,
                                            username = event.comment.username,
                                            photo = event.comment.photo,
                                            postId = event.postId,
                                            postImage = post.image,
                                            commentText = event.comment.text,
                                            type = NotificationType.Comment)
                                    notificationsRepo.createNotification(post.uid, notification)
                                            .addOnFailureListener {
                                                Log.d(TAG, "Failed to create notification", it)
                                            }
                                }
                    }
                }
            }
        })
    }

    private fun getUser(uid: String) = usersRepo.getUser(uid)

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    companion object {
        const val TAG = "NotificationsCreator"
    }
}