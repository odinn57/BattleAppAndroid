package com.odinn.application.common

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.odinn.application.models.Comment

object EventBus {
    //LiveData
    private val liveDataBus = MutableLiveData<Event>()

    val events: LiveData<Event> = liveDataBus
    fun publish(event: Event) {
        liveDataBus.value = event
    }
}

sealed class Event {
    data class CreateComment(val postId: String, val comment: Comment) : Event()
    data class CreateLike(val postId: String, val uid: String) : Event()
    data class CreateFollow(val fromUid: String, val toUid: String) : Event()
}