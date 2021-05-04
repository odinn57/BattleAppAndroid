package com.odinn.application.screens.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.odinn.application.data.UsersRepository

class ProfileViewModel(private val usersRepo: UsersRepository) : ViewModel() {
    val user = usersRepo.getUser()
    lateinit var images: LiveData<List<String>>

    fun init(uid: String){
        images = usersRepo.getImages(uid)
    }
}
