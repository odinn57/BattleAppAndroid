package com.odinn.application.activities.addfriends

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.odinn.application.R
import com.odinn.application.activities.ViewModelFactory
import com.odinn.application.activities.showToast
import com.odinn.application.models.User
import kotlinx.android.synthetic.main.activity_add_friends.*

class AddFriendsActivity : AppCompatActivity(), FriendsAdapter.Listener {
    private lateinit var mUser: User
    private lateinit var mUsers: List<User>
    private lateinit var mAdapter: FriendsAdapter
    private lateinit var mViewModel: AddFriendsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friends)

        mAdapter = FriendsAdapter(this)
        mViewModel = ViewModelProviders.of(this, ViewModelFactory())
                .get(AddFriendsViewModel::class.java)

        back_image.setOnClickListener { finish() }

        add_friends_recycler.adapter = mAdapter
        add_friends_recycler.layoutManager = LinearLayoutManager(this)

        mViewModel.userAndFriends.observe(this, Observer {
            it?.let { (user, otherUser) ->
                mUser = user
                mUsers = otherUser

                mAdapter.update(mUsers, mUser.follows)
                count_users.text = mAdapter.itemCount.toString()
            }
        })
//        mFirebase.database.child("users").addValueEventListener(ValueEventListenerAdapter {
//            val allUsers = it.children.map { it.asUser()!! }
//            val (userList, otherUsersList) = allUsers.partition { it.uid == uid }
//            mUser = userList.first()
//            mUsers = otherUsersList
//
//            mAdapter.update(mUsers, mUser.follows)
//            count_users.text = mAdapter.itemCount.toString()
//        })
    }

    override fun follow(uid: String) {
        setFollow(uid, true) {
            mAdapter.followed(uid)
        }
    }

    override fun unfollow(uid: String) {
        setFollow(uid, false) {
            mAdapter.unfollowed(uid)
        }
    }

    private fun setFollow(uid: String, follow: Boolean, onSuccess: () -> Unit) {
        mViewModel.setFollow(mUser.uid,uid, follow)
                .addOnSuccessListener{ onSuccess() }
                .addOnFailureListener{ showToast(it.message!!)}
    }

    companion object{
        const val TAG = "AddFriendsActivity"
    }
}