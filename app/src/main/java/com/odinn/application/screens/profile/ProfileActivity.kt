package com.odinn.application.screens.profile

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.odinn.application.R
import com.odinn.application.screens.addfriends.AddFriendsActivity
import com.odinn.application.screens.common.*
import com.odinn.application.screens.editprofile.EditProfileActivity
import com.odinn.application.screens.profilesettings.ProfileSettingsActivity
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity() {

    private lateinit var mAdapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupBottomNavigation(4)
        Log.d(TAG, "onCreate")

        edit_profile_btn.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
        settings_image.setOnClickListener {
            val intent = Intent(this, ProfileSettingsActivity::class.java)
            startActivity(intent)
        }
        add_friends_image.setOnClickListener {
            val intent = Intent(this, AddFriendsActivity::class.java)
            startActivity(intent)
        }
        images_recycler.layoutManager = GridLayoutManager(this, 3)
        mAdapter = ImagesAdapter()
        images_recycler.adapter = mAdapter
        setupAuthGuard { uid ->
            val viewModel = initViewModel<ProfileViewModel>()
            viewModel.init(uid)
            viewModel.user.observe(this, Observer {
                it?.let {
                    profile_image.loadUserPhoto(it.photo)
                    username_text.text = it.username
                    followers_count_text.text = it.followers.size.toString()
                    following_count_text.text = it.follows.size.toString()
                }
            })
            viewModel.images.observe(this, Observer {
                it?.let { images ->
                    mAdapter.updateImages(images)
                }
            })

        }

    }

    companion object {
        const val TAG = "ProfileActivity"
    }
}

