package com.odinn.application.screens.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnFailureListener
import com.odinn.application.screens.addfriends.AddFriendsViewModel
import com.odinn.application.data.firebase.FirebaseFeedPostsRepository
import com.odinn.application.screens.editprofile.EditProfileViewModel
import com.odinn.application.data.firebase.FirebaseUsersRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val onFailureListener: OnFailureListener) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom((AddFriendsViewModel::class.java))){
        return AddFriendsViewModel(onFailureListener,FirebaseUsersRepository() ,FirebaseFeedPostsRepository()) as T
        }else if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)){
            return EditProfileViewModel(onFailureListener,FirebaseUsersRepository()) as T
        }
        else{
            error("Unknown view model class $modelClass")
        }
    }
}