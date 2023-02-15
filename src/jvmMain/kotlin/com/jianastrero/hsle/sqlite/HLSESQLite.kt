package com.jianastrero.hsle.sqlite

import com.jianastrero.hsle.model.Field
import java.sql.Connection
import java.sql.DriverManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HLSESQLite private constructor(sqliteFilePath: String) {
    private val connection: Connection

    init {
        Class.forName("org.sqlite.JDBC")
        connection = DriverManager.getConnection("jdbc:sqlite:${sqliteFilePath}")
    }

    suspend fun <T> fetchAll(fields: List<Field<out T>>): List<Field<T>> = withContext(Dispatchers.IO) {
        val newFields = mutableListOf<Field<T>>()

        fields.forEach {
            val selectQuery = it.selectQuery()
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(selectQuery)
            while (resultSet.next()) {
                val value = when (it.value) {
                    is Int -> resultSet.getInt(it.valueColumn) ?: 0
                    is Long -> resultSet.getLong(it.valueColumn) ?: 0L
                    is Float -> resultSet.getFloat(it.valueColumn) ?: 0f
                    is Double -> resultSet.getDouble(it.valueColumn) ?: 0.0
                    else -> resultSet.getString(it.valueColumn) ?: ""
                } as T
                newFields.add(it.copy(value))
            }
        }

        newFields
    }

    suspend fun <T> updateField(field: Field<out T>): Boolean = withContext(Dispatchers.IO) {
        val statement = connection.createStatement()
        statement.executeUpdate(field.updateQuery()) > 0
    }

    private fun close() {
        connection.close()
    }

    companion object {
        private var instance: HLSESQLite? = null

        fun initiate(sqliteFilePath: String) {
            instance?.close()
            instance = HLSESQLite(sqliteFilePath)
        }

        fun getInstance(): HLSESQLite? = instance

        fun close() {
            instance?.close()
            instance = null
        }
    }
}
