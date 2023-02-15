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

    fun error(message: String) {
        _notifications = _notifications + Notification(
            type = NotificationType.Error,
            message = message
        )
    }

    fun success(message: String) {
        _notifications = _notifications + Notification(
            type = NotificationType.Success,
            message = message
        )
    }

    fun info(message: String) {
        _notifications = _notifications + Notification(
            type = NotificationType.Info,
            message = message
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