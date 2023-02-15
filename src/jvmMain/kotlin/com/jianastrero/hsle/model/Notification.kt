package com.jianastrero.hsle.model

import com.jianastrero.hsle.enumerations.NotificationType

data class Notification(
    val type: NotificationType,
    val message: String,
    val time: Long = System.currentTimeMillis()
)
