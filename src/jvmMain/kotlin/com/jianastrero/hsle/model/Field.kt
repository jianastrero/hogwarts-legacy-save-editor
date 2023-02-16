package com.jianastrero.hsle.model

sealed class Field<T>(
    open val title: String,
    open val table: String,
    open val identifiers: Array<Pair<String, String>>,
    open val valueColumn: String,
    open val value: T
) {

    fun selectQuery(): String {
        val whereClause = identifiers.joinToString(" AND ") { (column, value) -> "$column = \"$value\"" }
        return "SELECT $valueColumn FROM $table WHERE $whereClause"
    }

    fun updateQuery(): String {
        val whereClause = identifiers.joinToString(" AND ") { (column, value) -> "$column = \"$value\"" }
        return "UPDATE $table SET $valueColumn = \"$value\" WHERE $whereClause"
    }

    fun <T> copy(newValue: T): Field<T> = when (this) {
        is PersonalDataField.FirstName -> PersonalDataField.FirstName(value = newValue as String)
        is PersonalDataField.LastName -> PersonalDataField.LastName(value = newValue as String)
        is PersonalDataField.Experience -> PersonalDataField.Experience(value = newValue as Int)
        is PersonalDataField.Galleons -> PersonalDataField.Galleons(value = newValue as Int)
        is PersonalDataField.TalentPoints -> PersonalDataField.TalentPoints(value = newValue as Int)
        is PersonalDataField.House -> PersonalDataField.House(value = newValue as String)
        is AchievementField -> copy(value = newValue as Boolean)
        is ResourcesField -> copy(value = newValue as Int)
    } as Field<T>

    sealed class PersonalDataField<T>(
        title: String,
        table: String,
        identifiers: Array<Pair<String, String>>,
        valueColumn: String,
        value: T
    ) : Field<T>(title, table, identifiers, valueColumn, value) {
        class FirstName(value: String) : PersonalDataField<String>(
            title = "First Name",
            table = "MiscDataDynamic",
            identifiers = arrayOf("DataName" to "PlayerFirstName"),
            valueColumn = "DataValue",
            value = value
        )
        class LastName(value: String) : PersonalDataField<String>(
            title = "Last Name",
            table = "MiscDataDynamic",
            identifiers = arrayOf("DataName" to "PlayerLastName"),
            valueColumn = "DataValue",
            value = value
        )
        class Experience(value: Int) : PersonalDataField<Int>(
            title = "Experience",
            table = "MiscDataDynamic",
            identifiers = arrayOf("DataName" to "ExperiencePoints"),
            valueColumn = "DataValue",
            value = value
        )
        class Galleons(value: Int) : PersonalDataField<Int>(
            title = "Galleons",
            table = "InventoryDynamic",
            identifiers = arrayOf("CharacterID" to "Player0", "ItemID" to "Knuts"),
            valueColumn = "Count",
            value = value
        )
        class TalentPoints(value: Int) : PersonalDataField<Int>(
            title = "Talent Points",
            table = "MiscDataDynamic",
            identifiers = arrayOf("DataOwner" to "Player0", "DataName" to "PerkPoints"),
            valueColumn = "DataValue",
            value = value
        )
        class House(value: String) : PersonalDataField<String>(
            title = "House",
            table = "MiscDataDynamic",
            identifiers = arrayOf("DataName" to "HouseID"),
            valueColumn = "DataValue",
            value = value
        )
    }

    data class ResourcesField(
        override val title: String,
        override val table: String,
        override val identifiers: Array<Pair<String, String>>,
        override val valueColumn: String,
        override val value: Int
    ) : Field<Int>(title, table, identifiers, valueColumn, value) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ResourcesField

            if (title != other.title) return false
            if (table != other.table) return false
            if (!identifiers.contentEquals(other.identifiers)) return false
            if (valueColumn != other.valueColumn) return false
            if (value != other.value) return false

            return true
        }
        override fun hashCode(): Int {
            var result = title.hashCode()
            result = 31 * result + table.hashCode()
            result = 31 * result + identifiers.contentHashCode()
            result = 31 * result + valueColumn.hashCode()
            result = 31 * result + value
            return result
        }
        companion object {
            fun values() = this::class.java.getResource("/lists/resources.txt")
                ?.readText()
                ?.split("\\s+".toRegex())
                ?.sorted()
                ?.map { itemId ->
                    val title = itemId.replace("_Byproduct", "")
                        .replace("_", " ")
                        .split("[\\sA-Z]+")
                        .joinToString(" ")
                        .trim()
                    ResourcesField(
                        title = title,
                        table = "InventoryDynamic",
                        identifiers = arrayOf(
                            "CharacterID" to "Player0",
                            "HolderID" to "ResourceInventory",
                            "ItemID" to itemId
                        ),
                        valueColumn = "Count",
                        value = 0
                    )
                }
                ?: emptyList()
        }
    }

    data class AchievementField(
        override val title: String,
        override val table: String,
        override val identifiers: Array<Pair<String, String>>,
        override val valueColumn: String,
        override val value: Boolean
    ) : Field<Boolean>(title, table, identifiers, valueColumn, value) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as AchievementField

            if (title != other.title) return false
            if (table != other.table) return false
            if (!identifiers.contentEquals(other.identifiers)) return false
            if (valueColumn != other.valueColumn) return false
            if (value != other.value) return false

            return true
        }
        override fun hashCode(): Int {
            var result = title.hashCode()
            result = 31 * result + table.hashCode()
            result = 31 * result + identifiers.contentHashCode()
            result = 31 * result + valueColumn.hashCode()
            result = 31 * result + value.hashCode()
            return result
        }
    }
}