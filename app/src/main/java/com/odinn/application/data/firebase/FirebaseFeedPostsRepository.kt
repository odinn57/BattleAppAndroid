package com.odinn.application.data.firebase

import com.google.android.gms.tasks.Task
import com.odinn.application.common.toUnit
import com.odinn.application.common.task
import com.odinn.application.data.FeedPostsRepository
import com.odinn.application.common.TaskSourceOnCompleteListener
import com.odinn.application.common.ValueEventListenerAdapter
import com.odinn.application.data.firebase.common.database

class FirebaseFeedPostsRepository : FeedPostsRepository {
    override fun copyFeedPosts(postsAuthorUid: String, uid: String): Task<Unit>
        = task { taskSource ->
        database.child("feed-posts").child(postsAuthorUid)
                .orderByChild("uid")
                .equalTo(postsAuthorUid)
                .addListenerForSingleValueEvent(ValueEventListenerAdapter {
                    val postsMap = it.children.map { it.key to it.value }.toMap()
                    database.child("feed-posts").child(uid).updateChildren(postsMap)
                            .toUnit()
                            .addOnCompleteListener(TaskSourceOnCompleteListener(taskSource))
                })
    }

    override fun deleteFeedPosts(postsAuthorUid: String, uid: String): Task<Unit>
        = task { taskSource ->
        database.child("feed-posts").child(postsAuthorUid)
                .orderByChild("uid")
                .equalTo(postsAuthorUid)
                .addListenerForSingleValueEvent(ValueEventListenerAdapter {
                    val postsMap = it.children.map { it.key to null }.toMap()
                    database.child("feed-posts").child(uid).updateChildren(postsMap)
                            .toUnit()
                            .addOnCompleteListener(TaskSourceOnCompleteListener(taskSource))
                })
    }

}