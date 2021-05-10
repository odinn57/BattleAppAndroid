package com.odinn.application.screens

import android.app.Application
import com.odinn.application.common.firebase.FirebaseAuthManager
import com.odinn.application.data.NotificationsRepository
import com.odinn.application.data.firebase.FirebaseFeedPostsRepository
import com.odinn.application.data.firebase.FirebaseNotificationsRepository
import com.odinn.application.data.firebase.FirebaseUsersRepository
import com.odinn.application.screens.notifications.NotificationsCreator

class PhotoBattleApp : Application() {
    val feedPostsRepo by lazy { FirebaseFeedPostsRepository() }
    val usersRepo by lazy { FirebaseUsersRepository() }
    val notificationsRepo by lazy { FirebaseNotificationsRepository() }
    val authManager by lazy { FirebaseAuthManager() }

    override fun onCreate() {
        super.onCreate()
        NotificationsCreator(notificationsRepo, usersRepo,feedPostsRepo)
    }
}