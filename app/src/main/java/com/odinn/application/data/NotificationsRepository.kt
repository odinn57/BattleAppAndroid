package com.odinn.application.data

import android.arch.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.odinn.application.models.Notification

interface NotificationsRepository {

    fun createNotification(uid: String, notification: Notification): Task<Unit>
    fun getNotifications(uid: String): LiveData<List<Notification>>
    fun setNotificationsRead(uid: String, ids: List<String>, read: Boolean): Task<Unit>
}