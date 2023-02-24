@file:Suppress("UnstableApiUsage")

package com.jianastrero.hsle.extensions

import com.google.common.io.LittleEndianDataInputStream as DataInputStream
import com.google.common.io.LittleEndianDataOutputStream as DataOutputStream

fun DataInputStream.readString() =
    readInt().let {
        if (it !in -512..512) {
            throw RuntimeException("String length too long: $it")
        }

        if (it < 0) {
            readNBytes(it * -2)
        } else {
            readNBytes(it)
        }.decodeToString().trim(Char.MIN_VALUE)
    }

fun DataOutputStream.writeString(string: String) {
    if (string.isNotEmpty()) {
        if (string.toByteArray(Charsets.UTF_8).size == string.length) {
            write(string.length + 1)
            writeChars(string)
        } else {
            val bytes = string.toByteArray(Charsets.UTF_16LE)
            write(-1 - bytes.size / 2)
            write(bytes)
        }
    }
    writeChar(0)
}