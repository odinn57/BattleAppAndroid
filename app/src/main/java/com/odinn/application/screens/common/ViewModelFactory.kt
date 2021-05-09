package com.odinn.application.screens.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnFailureListener
import com.odinn.application.common.firebase.FirebaseAuthManager
import com.odinn.application.data.firebase.FirebaseFeedPostsRepository
import com.odinn.application.data.firebase.FirebaseUsersRepository
import com.odinn.application.screens.PhotoBattleApp
import com.odinn.application.screens.addfriends.AddFriendsViewModel
import com.odinn.application.screens.comments.CommentsViewModel
import com.odinn.application.screens.editprofile.EditProfileViewModel
import com.odinn.application.screens.home.HomeViewModel
import com.odinn.application.screens.login.LoginViewModel
import com.odinn.application.screens.profile.ProfileViewModel
import com.odinn.application.screens.profilesettings.ProfileSettingsViewModel
import com.odinn.application.screens.register.RegisterViewModel
import com.odinn.application.screens.share.ShareViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val app: PhotoBattleApp,
                       private val commonViewModel: CommonViewModel,
                       private val onFailureListener: OnFailureListener) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val feedPostsRepo = app.feedPostsRepo
        val usersRepo = app.usersRepo
        val authManager = app.authManager

        if (modelClass.isAssignableFrom(AddFriendsViewModel::class.java)) {
            return AddFriendsViewModel(onFailureListener, usersRepo, feedPostsRepo) as T
        } else if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            return EditProfileViewModel(onFailureListener, usersRepo) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(onFailureListener, feedPostsRepo) as T
        } else if (modelClass.isAssignableFrom(ProfileSettingsViewModel::class.java)) {
            return ProfileSettingsViewModel(authManager, onFailureListener) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(authManager, app, commonViewModel, onFailureListener) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(usersRepo,onFailureListener) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(commonViewModel, app,onFailureListener, usersRepo) as T
        } else if (modelClass.isAssignableFrom(ShareViewModel::class.java)) {
            return ShareViewModel(feedPostsRepo,usersRepo, onFailureListener) as T
        } else if(modelClass.isAssignableFrom(CommentsViewModel::class.java)){
            return CommentsViewModel(feedPostsRepo, usersRepo, onFailureListener) as T
        }
        else {
            error("Unknown view model class $modelClass")
        }
    }
}