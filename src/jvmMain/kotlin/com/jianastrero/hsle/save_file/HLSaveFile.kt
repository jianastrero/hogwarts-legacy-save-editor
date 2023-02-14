package com.jianastrero.hsle.save_file

import com.jianastrero.hsle.extensions.findFirst
import com.jianastrero.hsle.extensions.littleEndian
import com.jianastrero.hsle.model.HLSaveFileData
import java.io.File

class HLSaveFile {

    fun read(filePath: String): HLSaveFileData {
        val bytes = File(filePath).readBytes()

        val rawDbImageStartIndex = bytes.findFirst(RAW_DB_IMAGE_BYTES)
        val dbSizeOffset = rawDbImageStartIndex + 61
        val dbStartOffset = dbSizeOffset + 4
        val dbSizeBytes = bytes.copyOfRange(dbSizeOffset, dbStartOffset)
        val dbSize = dbSizeBytes.littleEndian()
        val dbEndOffset = dbStartOffset + dbSize

        generateTempFolder()
        val tempSqliteFilePath = "${TEMP_FOLDER}${File.separatorChar}temp-${System.currentTimeMillis()}.sqlite"
        val tempSqliteFile = File(tempSqliteFilePath)
        tempSqliteFile.outputStream().use {
            it.write(bytes.copyOfRange(dbStartOffset, dbEndOffset))
            it.flush()
        }

        return HLSaveFileData(
            tempSqliteFilePath = tempSqliteFilePath,
            saveFileBytes = bytes,
            rawDbImageStartIndex = rawDbImageStartIndex,
            dbEndOffset = dbEndOffset
        )
    }

    fun write(filePath: String, hlSaveFileData: HLSaveFileData) {
        val tempSqliteBytes = File(hlSaveFileData.tempSqliteFilePath).readBytes()
        val newSaveFile = File(filePath)
        newSaveFile.outputStream().use {
            // Put original save file initial padding
            it.write(hlSaveFileData.saveFileBytes.copyOfRange(0, hlSaveFileData.rawDbImageStartIndex + 35))

            // Write sqlite size + 4 in little endian
            it.write((tempSqliteBytes.size + 4).littleEndian())

            // Write padding bytes from original file
            it.write(
                hlSaveFileData.saveFileBytes
                    .copyOfRange(hlSaveFileData.rawDbImageStartIndex + 39, hlSaveFileData.rawDbImageStartIndex + 61)
            )

            // Write sqlite size in little endian
            it.write(tempSqliteBytes.size.littleEndian())

            // Write updated db
            it.write(tempSqliteBytes)

            // Save end padding
            it.write(
                hlSaveFileData.saveFileBytes
                    .copyOfRange(hlSaveFileData.dbEndOffset, hlSaveFileData.saveFileBytes.size)
            )
        }
    }

    companion object {
        private val RAW_DB_IMAGE_BYTES = byteArrayOf(
            0x52, 0x61, 0x77, 0x44,
            0x61, 0x74, 0x61, 0x62,
            0x61, 0x73, 0x65, 0x49,
            0x6D, 0x61, 0x67, 0x65
        )
        private const val TEMP_FOLDER = "temp"

        private fun generateTempFolder() {
            try {
                val tempFolder = File(TEMP_FOLDER)
                tempFolder.mkdirs()
                tempFolder.listFiles()?.forEach {
                    it.deleteRecursively()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}