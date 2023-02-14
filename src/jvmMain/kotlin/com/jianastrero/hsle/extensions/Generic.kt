package com.jianastrero.hsle.extensions

fun <T, R> T.getGenericValue(): R =
    when (this) {
        is Int -> this.toString().toIntOrNull() ?: 0
        is Long -> this.toString().toLongOrNull() ?: 0L
        is Float -> this.toString().toFloatOrNull() ?: 0f
        is Double -> this.toString().toDoubleOrNull() ?: 0.0
        else -> this.toString()
    } as R