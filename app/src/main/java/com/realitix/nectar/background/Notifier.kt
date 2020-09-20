package com.realitix.nectar.background

import android.content.Context
import com.realitix.nectar.util.NectarUtil

interface NotifierInterface {
    fun prepare()
    fun addNotification(notification: String)
    fun notify(context: Context)
}


class DefaultNotifier: NotifierInterface {
    private val notifications = mutableListOf<String>()

    override fun prepare() {
        notifications.clear()
    }

    override fun addNotification(notification: String) {
        notifications.add(notification)
    }

    override fun notify(context: Context) {
        if(notifications.size > 0) {
            NectarUtil.showNotification(context, notifications.joinToString("\n"))
        }
    }
}


class DummyNotifier: NotifierInterface {
    override fun prepare() {
    }

    override fun addNotification(notification: String) {
    }

    override fun notify(context: Context) {
    }
}