package com.jianastrero.hsle.notification

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.jianastrero.hsle.enumerations.NotificationType
import com.jianastrero.hsle.model.Notification

object Notifications {

    private var _notifications by mutableStateOf(listOf<Notification>())
    val notifications: List<Notification> by derivedStateOf { _notifications }

    fun error(message: String, time: Long = System.currentTimeMillis()) {
        _notifications = _notifications + Notification(
            type = NotificationType.Error,
            message = message,
            time = time
        )
    }

    fun success(message: String, time: Long = System.currentTimeMillis()) {
        _notifications = _notifications + Notification(
            type = NotificationType.Success,
            message = message,
            time = time
        )
    }

    fun info(message: String, time: Long = System.currentTimeMillis()) {
        _notifications = _notifications + Notification(
            type = NotificationType.Info,
            message = message,
            time = time
        )
    }

    fun pop(): Notification? {
        if (_notifications.isEmpty()) {
            return null
        }

        val notification = _notifications.firstOrNull()
        _notifications = _notifications.subList(1, _notifications.size)
        return notification
    }

    fun peek(): Notification? {
        if (_notifications.isEmpty()) {
            return null
        }

        return _notifications.firstOrNull()
    }

}