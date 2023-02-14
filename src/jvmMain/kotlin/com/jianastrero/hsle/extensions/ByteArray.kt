package com.jianastrero.hsle.extensions

import java.nio.ByteBuffer
import java.nio.ByteOrder

fun ByteArray.findFirst(sequence: ByteArray, startFrom: Int = 0): Int {
    if(sequence.isEmpty()) throw IllegalArgumentException("non-empty byte sequence is required")
    if(startFrom < 0 ) throw IllegalArgumentException("startFrom must be non-negative")
    var matchOffset = 0
    var start = startFrom
    var offset = startFrom
    while( offset < size ) {
        if( this[offset] == sequence[matchOffset]) {
            if( matchOffset++ == 0 ) start = offset
            if( matchOffset == sequence.size ) return start
        }
        else
            matchOffset = 0
        offset++
    }
    return -1
}

fun ByteArray.littleEndian(): Int {
    var x = 0
    val byteBuffer = ByteBuffer.wrap(this)
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
    while (byteBuffer.hasRemaining()) {
        x += byteBuffer.int
    }
    return x
}

fun Int.littleEndian(): ByteArray {
    val byteBuffer = ByteBuffer.allocate(4)
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
    byteBuffer.putInt(this)
    return byteBuffer.array()
}