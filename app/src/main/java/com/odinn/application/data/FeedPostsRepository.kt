package com.odinn.application.data

import android.arch.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.odinn.application.models.FeedPost

interface FeedPostsRepository {
    fun copyFeedPosts(postsAuthorUid: String, uid: String): Task<Unit>
    fun deleteFeedPosts(postsAuthorUid: String, uid: String): Task<Unit>
    fun getFeedPosts(uid: String): LiveData<List<FeedPost>>
    fun toggleLike(postId: String, uid: String) : Task<Unit>
    fun getLikes(postId: String) : LiveData<List<FeedPostLike>>
}

data class FeedPostLike(val userId: String)