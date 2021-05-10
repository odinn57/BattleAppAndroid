package com.odinn.application.screens.notifications

import android.arch.lifecycle.LiveData
import com.google.android.gms.tasks.OnFailureListener
import com.odinn.application.data.NotificationsRepository
import com.odinn.application.models.Notification
import com.odinn.application.screens.common.BaseViewModel

class NotificationsViewModel(private val notificationsRepo: NotificationsRepository,
                             onFailureListener: OnFailureListener) : BaseViewModel(onFailureListener) {
    lateinit var notifications: LiveData<List<Notification>>
    private lateinit var uid: String

    fun init(uid: String) {
        this.uid = uid
        notifications = notificationsRepo.getNotifications(uid)
    }

    fun setNotificationsRead(notifications: List<Notification>) {
        val ids = notifications.filter { !it.read }.map { it.id }
        if (ids.isNotEmpty()) {
            notificationsRepo.setNotificationsRead(uid, ids, true)
                    .addOnFailureListener(onFailureListener)
        }
    }

}