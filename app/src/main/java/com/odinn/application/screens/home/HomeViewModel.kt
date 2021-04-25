package com.odinn.application.screens.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.odinn.application.data.FeedPostsRepository
import com.odinn.application.data.common.map
import com.odinn.application.models.FeedPost

class HomeViewModel(private val onFailureListener : OnFailureListener,
                    private val feedPostsRepo : FeedPostsRepository) : ViewModel() {
    lateinit var uid: String
    lateinit var feedPosts: LiveData<List<FeedPost>>
    private var loadedLikes = mapOf<String, LiveData<FeedPostLikes>>()

    fun init(uid: String) {
        this.uid = uid
        feedPosts = feedPostsRepo.getFeedPosts(uid).map {
            it.sortedByDescending { it.timestampDate() }
        }
    }

    fun toggleLike(postId: String) {
        feedPostsRepo.toggleLike(postId, uid).addOnFailureListener(onFailureListener)
    }

    fun getLikes(postId: String): LiveData<FeedPostLikes>? = loadedLikes[postId]

    fun loadLikes(postId: String): LiveData<FeedPostLikes> {
        val existingloadedLikes = loadedLikes[postId]
        if ( existingloadedLikes == null) {
            val liveData = feedPostsRepo.getLikes(postId).map { likes ->
                FeedPostLikes(
                        likesCount = likes.size,
                        likedByUser = likes.find { it.userId == uid } != null
                )
            }
            loadedLikes += postId to liveData
            return liveData
        }else{
            return existingloadedLikes
        }
    }
}

