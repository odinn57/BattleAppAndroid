package com.odinn.application.common.firebase

import com.odinn.application.common.AuthManager
import com.odinn.application.data.firebase.common.auth

class FirebaseAuthManager : AuthManager {
    override fun signOut() {
        auth.signOut()
    }
}