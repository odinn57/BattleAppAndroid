package com.odinn.application.screens.editprofile

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.odinn.application.R
import com.odinn.application.models.User
import com.odinn.application.screens.common.BaseActivity
import com.odinn.application.screens.common.loadUserPhoto
import com.odinn.application.screens.common.showToast
import com.odinn.application.screens.common.toStringOrNull
import com.odinn.application.screens.common.CameraHelper
import com.odinn.application.screens.common.PasswordDialog
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : BaseActivity(), PasswordDialog.Listener {

    private lateinit var mUser: User
    private lateinit var mPendingUser: User
    private lateinit var mCamera: CameraHelper
    private lateinit var mViewModel: EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        Log.d(TAG, "onCreate")

        mCamera = CameraHelper(this)

        back_image.setOnClickListener { finish() }
        save_image.setOnClickListener { updateProfile() }
        change_photo_text.setOnClickListener { mCamera.takeCameraPicture() }

        mViewModel = initViewModel()

        mViewModel.user.observe(this, Observer{it?.let{
            mUser = it
            name_input.setText(mUser.name)
            username_input.setText(mUser.username)
            website_input.setText(mUser.website)
            bio_input.setText(mUser.bio)
            email_input.setText(mUser.email)
            phone_input.setText(mUser.phone?.toString())
            profile_image.loadUserPhoto(mUser.photo)
        }})
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == mCamera.REQUEST_CODE && resultCode == RESULT_OK) {
            mViewModel.uploadAndSetUserPhoto(mCamera.imageUri!!)
        }
    }

    private fun updateProfile() {
        mPendingUser = readInputs()
        val error = validate(mPendingUser)
        if (error == null) {
            if (mPendingUser.email == mUser.email) {
                updateUser(mPendingUser)
            } else {
                PasswordDialog().show(supportFragmentManager, "password_dialog")
            }
        } else {
            showToast(error)
        }
    }

    private fun readInputs(): User {
        return User(
                name = name_input.text.toString(),
                username = username_input.text.toString(),
                email = email_input.text.toString(),
                website = website_input.text.toStringOrNull(),
                bio = bio_input.text.toStringOrNull(),
                phone = phone_input.text.toString().toLongOrNull()
        )
    }

    override fun onPasswordConfirm(password: String) {
        if (password.isNotEmpty()) {
            mViewModel.updateEmail(
                    currentEmail = mUser.email,
                    newEmail = mPendingUser.email,
                    password = password)
                    .addOnSuccessListener { updateUser(mPendingUser) }
        } else {
            showToast(getString(R.string.you_should_enter_your_password))
        }
    }

    private fun updateUser(user: User) {
        mViewModel.updateUserProfile(currentUser = mUser, newUser = user)
                .addOnSuccessListener {
                    showToast(getString(R.string.profile_saved))
                    finish()
                }
    }

    private fun validate(user: User): String? =
            when {
                user.name.isEmpty() -> getString(R.string.please_enter_name)
                user.username.isEmpty() -> getString(R.string.please_enter_username)
                user.email.isEmpty() -> getString(R.string.please_enter_email)
                else -> null
            }

    companion object{
        const val TAG = "EditProfileActivity"
    }
}