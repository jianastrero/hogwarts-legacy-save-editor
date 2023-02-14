package com.jianastrero.hsle.model

data class HLSaveFileData(
    val tempSqliteFilePath: String,
    val saveFileBytes: ByteArray,
    val rawDbImageStartIndex: Int,
    val dbEndOffset: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HLSaveFileData

        if (tempSqliteFilePath != other.tempSqliteFilePath) return false
        if (!saveFileBytes.contentEquals(other.saveFileBytes)) return false
        if (rawDbImageStartIndex != other.rawDbImageStartIndex) return false
        if (dbEndOffset != other.dbEndOffset) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tempSqliteFilePath.hashCode()
        result = 31 * result + saveFileBytes.contentHashCode()
        result = 31 * result + rawDbImageStartIndex
        result = 31 * result + dbEndOffset
        return result
    }
}
