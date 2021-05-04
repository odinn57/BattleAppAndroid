package com.odinn.application.common.firebase

import com.google.android.gms.tasks.Task
import com.odinn.application.common.AuthManager
import com.odinn.application.common.toUnit
import com.odinn.application.data.firebase.common.auth

class FirebaseAuthManager : AuthManager {
    override fun signOut() {
        auth.signOut()
    }

    override fun signIn(email: String, password: String): Task<Unit> =
        auth.signInWithEmailAndPassword(email, password).toUnit()

}