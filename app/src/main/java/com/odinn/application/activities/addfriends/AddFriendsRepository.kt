package com.odinn.application.activities.addfriends

import android.arch.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.odinn.application.activities.asUser
import com.odinn.application.activities.map
import com.odinn.application.activities.task
import com.odinn.application.models.User
import com.odinn.application.utils.*

interface AddFriendsRepository {
    fun getUsers(): LiveData<List<User>>
    fun currentUid(): String?
    fun addFollow(fromUid: String, toUid: String): Task<Unit>
    fun deleteFollow(fromUid: String, toUid: String): Task<Unit>
    fun addFollower(fromUid: String, toUid: String): Task<Unit>
    fun deleteFollower(fromUid: String, toUid: String): Task<Unit>
    fun copyFeedPosts(postsAuthorUid: String, uid: String): Task<Unit>
    fun deleteFeedPosts(postsAuthorUid: String, uid: String): Task<Unit>
}

class FirebaseAddFriendsRepository : AddFriendsRepository {
    override fun getUsers(): LiveData<List<User>> =
        database.child("users").liveData().map {
            it.children.map { it.asUser()!! }
        }

    override fun addFollow(fromUid: String, toUid: String): Task<Unit>
            = getFollowsRef(fromUid, toUid).setValue(true).toUnit()


    override fun deleteFollow(fromUid: String, toUid: String): Task<Unit>
            = getFollowsRef(fromUid, toUid).removeValue().toUnit()

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
                        val postsMap = it.children.map { it.key to null}.toMap()
                        database.child("feed-posts").child(uid).updateChildren(postsMap)
                                .toUnit()
                                .addOnCompleteListener(TaskSourceOnCompleteListener(taskSource))
                    })
        }

    override fun addFollower(fromUid: String, toUid: String): Task<Unit>
            = getFollowersRef(fromUid, toUid).setValue(true).toUnit()


    override fun deleteFollower(fromUid: String, toUid: String): Task<Unit>
            = getFollowersRef(fromUid, toUid).removeValue().toUnit()


    private fun getFollowsRef(fromUid: String, toUid: String)
            = database.child("users").child(fromUid).child("follows").child(toUid)

    private fun getFollowersRef(fromUid: String, toUid: String)
            = database.child("users").child(toUid).child("followers").child(fromUid)

    override fun currentUid()
            = FirebaseAuth.getInstance().currentUser?.uid
}

fun Task<Void>.toUnit(): Task<Unit> = onSuccessTask { Tasks.forResult(Unit)}