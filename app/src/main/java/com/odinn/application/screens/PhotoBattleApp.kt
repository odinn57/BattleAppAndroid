package com.odinn.application.screens

import android.app.Application
import com.odinn.application.common.firebase.FirebaseAuthManager
import com.odinn.application.data.firebase.FirebaseFeedPostsRepository
import com.odinn.application.data.firebase.FirebaseUsersRepository

class PhotoBattleApp : Application() {
    val feedPostsRepo by lazy { FirebaseFeedPostsRepository() }
    val usersRepo by lazy { FirebaseUsersRepository() }
    val authManager by lazy { FirebaseAuthManager() }
}