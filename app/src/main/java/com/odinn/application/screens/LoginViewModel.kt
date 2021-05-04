package com.odinn.application.screens

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.odinn.application.R
import com.odinn.application.common.AuthManager
import com.odinn.application.common.SingleLiveEvent
import com.odinn.application.screens.common.CommonViewModel

class LoginViewModel( private val authManager: AuthManager,
                      private val app: Application,
                      private val commonViewModel: CommonViewModel,
                      private val onFailureListener: OnFailureListener): ViewModel(){
    private val _goToHomeScreen = SingleLiveEvent<Unit>()
    val goToHomeScreen: LiveData<Unit> = _goToHomeScreen
    private val _goToRegisterScreen = SingleLiveEvent<Unit>()
    val goToRegisterScreen: LiveData<Unit> = _goToRegisterScreen

    fun onLoginClick(email: String, password: String) {
        if (validate(email, password)) {
            authManager.signIn(email, password).addOnSuccessListener{
                _goToHomeScreen.call()
            }.addOnFailureListener(onFailureListener)
        } else {
            commonViewModel.setErrorMessage(app.getString(R.string.please_enter_email_and_password))
        }
    }

    private fun validate(email: String, password: String) =
            email.isNotEmpty() && password.isNotEmpty()

    fun onRegisterClick() {
        _goToRegisterScreen.call()
    }

}
