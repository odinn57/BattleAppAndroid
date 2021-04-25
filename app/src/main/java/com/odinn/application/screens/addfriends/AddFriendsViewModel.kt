package com.odinn.application.screens.addfriends

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.odinn.application.data.common.map
import com.odinn.application.data.FeedPostsRepository
import com.odinn.application.data.UsersRepository
import com.odinn.application.models.User

class AddFriendsViewModel(private val onFailureListener: OnFailureListener,
                          private val usersRepo : UsersRepository,
                          private val feedPostsRepo: FeedPostsRepository) : ViewModel() {
    val userAndFriends: LiveData<Pair<User, List<User>>> =
            usersRepo.getUsers().map{ allUsers ->
                val (userList, otherUsersList) = allUsers.partition {
                    it.uid == usersRepo.currentUid()
                }
                userList.first() to otherUsersList
            }

    fun setFollow(currentUid:String, uid: String, follow: Boolean): Task<Void> {
        return (if (follow){
            Tasks.whenAll(
                    usersRepo.addFollow(currentUid, uid),
                    usersRepo.addFollower(currentUid, uid),
                    feedPostsRepo.copyFeedPosts(postsAuthorUid = uid, uid = currentUid)
            )
        }else{
            Tasks.whenAll(
                    usersRepo.deleteFollow(currentUid, uid),
                    usersRepo.deleteFollower(currentUid, uid),
                    feedPostsRepo.deleteFeedPosts(postsAuthorUid = uid, uid = currentUid)
            )
        }).addOnFailureListener(onFailureListener)
    }
}