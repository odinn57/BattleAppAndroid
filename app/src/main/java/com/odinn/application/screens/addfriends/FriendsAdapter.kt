package com.odinn.application.screens.addfriends

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.odinn.application.R
import com.odinn.application.screens.common.loadUserPhoto
import com.odinn.application.models.User
import kotlinx.android.synthetic.main.add_friends_item.view.*

class FriendsAdapter(private val listener: Listener)
    : RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    interface Listener {
        fun follow(uid: String)
        fun unfollow(uid: String)
    }

    private var mUsers = listOf<User>()
    private var mPositions = mapOf<String, Int>()
    private var mFollows = mapOf<String, Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.add_friends_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val user = mUsers[position]
            view.photo_image.loadUserPhoto(user.photo)
            view.username_text.text = user.username
            view.name_text.text = user.name
            view.follow_btn.setOnClickListener { listener.follow(user.uid) }
            view.unfollow_btn.setOnClickListener { listener.unfollow(user.uid) }

            val follows = mFollows[user.uid] ?: false
            if (follows) {
                view.follow_btn.visibility = View.GONE
                view.unfollow_btn.visibility = View.VISIBLE
            } else {
                view.follow_btn.visibility = View.VISIBLE
                view.unfollow_btn.visibility = View.GONE
            }
        }
    }

    override fun getItemCount() = mUsers.size

    fun update(users: List<User>, follows: Map<String, Boolean>) {
        mUsers = users
        mPositions = users.withIndex().map { (idx, user) -> user.uid to idx }.toMap()
        mFollows = follows

        this.notifyDataSetChanged()
    }

    fun followed(uid: String) {
        mFollows += (uid to true)
        notifyItemChanged(mPositions[uid]!!)
    }

    fun unfollowed(uid: String) {
        mFollows -= uid
        notifyItemChanged(mPositions[uid]!!)
    }
}