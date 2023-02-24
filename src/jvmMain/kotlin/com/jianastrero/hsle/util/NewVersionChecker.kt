package com.jianastrero.hsle.util

import com.jianastrero.hsle.Constants
import java.net.HttpURLConnection
import java.net.URL

fun getLatestVersion(): String = (URL(Constants.LATEST_RELEASE_URL).openConnection() as HttpURLConnection)
    .inputStream
    .use { it.readBytes() }
    .decodeToString()
    .trim()

fun getCurrentVersion(): String? =
    Constants::class.java.classLoader.getResource("version.txt")?.readText()?.trim()