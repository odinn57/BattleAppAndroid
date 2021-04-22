package com.odinn.application.data

import android.arch.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.odinn.application.models.User

interface FeedPostsRepository {
    fun copyFeedPosts(postsAuthorUid: String, uid: String): Task<Unit>
    fun deleteFeedPosts(postsAuthorUid: String, uid: String): Task<Unit>
}