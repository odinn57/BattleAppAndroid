package com.odinn.application.screens.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.odinn.application.data.UsersRepository
import com.odinn.application.screens.common.BaseViewModel

class ProfileViewModel(private val usersRepo: UsersRepository,
                       onFailureListener: OnFailureListener) : BaseViewModel(onFailureListener) {
    val user = usersRepo.getUser()
    lateinit var images: LiveData<List<String>>

    fun init(uid: String){
        images = usersRepo.getImages(uid)
    }
}
