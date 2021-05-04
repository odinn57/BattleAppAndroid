package com.odinn.application.screens.common

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import java.lang.Exception


class CommonViewModel : ViewModel(), OnFailureListener {
    private val _errorMessage : MutableLiveData<String> = MutableLiveData()
    val errorMessage : LiveData<String> = _errorMessage

    override fun onFailure(p0: Exception) {
        setErrorMessage(p0.message)
    }

    fun setErrorMessage(message: String?) {
        message?.let{_errorMessage.value = message}
    }
}