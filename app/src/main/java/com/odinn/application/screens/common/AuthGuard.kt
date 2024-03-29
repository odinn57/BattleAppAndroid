package com.odinn.application.screens.common

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.google.firebase.auth.FirebaseAuth
import com.odinn.application.data.firebase.common.auth

class AuthGuard(private val activity: BaseActivity, f : (String) -> Unit) : LifecycleObserver{
    init {
        val user = auth.currentUser
        if ( user == null ){
            activity.goToLogin()
        }else{
            f(user.uid)
            activity.lifecycle.addObserver( this)
        }
    }

    private val listener = FirebaseAuth.AuthStateListener {
        if (it.currentUser == null) {
            activity.goToLogin()
        }
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(){
        auth.addAuthStateListener(listener)
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(){
        auth.removeAuthStateListener(listener)
    }
}

fun BaseActivity.setupAuthGuard(f : (String) -> Unit){
    AuthGuard(this, f)
}