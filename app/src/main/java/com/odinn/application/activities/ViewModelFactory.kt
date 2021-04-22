package com.odinn.application.activities

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.odinn.application.activities.addfriends.AddFriendsViewModel
import com.odinn.application.activities.addfriends.FirebaseAddFriendsRepository
import com.odinn.application.activities.editprofile.EditProfileRepository
import com.odinn.application.activities.editprofile.EditProfileViewModel
import com.odinn.application.activities.editprofile.FirebaseEditProfileRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom((AddFriendsViewModel::class.java))){
        return AddFriendsViewModel(FirebaseAddFriendsRepository()) as T
        }else if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)){
            return EditProfileViewModel(FirebaseEditProfileRepository()) as T
        }
        else{
            error("Unknown view model class $modelClass")
        }
    }
}