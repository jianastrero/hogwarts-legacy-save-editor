package com.jianastrero.hsle.model

sealed class Field<T>(
    val title: String,
    val table: String,
    val identifiers: Array<Pair<String, String>>,
    val valueColumn: String,
    val value: T
) {

    fun selectQuery(): String {
        val whereClause = identifiers.joinToString(" AND ") { (column, value) -> "$column = \"$value\"" }
        return "SELECT $valueColumn FROM $table WHERE $whereClause"
    }

    fun updateQuery(): String {
        val whereClause = identifiers.joinToString(" AND ") { (column, value) -> "$column = \"$value\"" }
        return "UPDATE $table SET $valueColumn = \"$value\" WHERE $whereClause"
    }

    fun <T> copy(value: T): Field<T> = when (this) {
        is FirstName -> FirstName(value = value as String)
        is LastName -> LastName(value = value as String)
        is Experience -> Experience(value = value as Int)
        is Galleons -> Galleons(value = value as Int)
        is TalentPoints -> TalentPoints(value = value as Int)
    } as Field<T>

    class FirstName(value: String) : Field<String>(
        title = "First Name",
        table = "MiscDataDynamic",
        identifiers = arrayOf("DataName" to "PlayerFirstName"),
        valueColumn = "DataValue",
        value = value
    )
    class LastName(value: String) : Field<String>(
        title = "Last Name",
        table = "MiscDataDynamic",
        identifiers = arrayOf("DataName" to "PlayerLastName"),
        valueColumn = "DataValue",
        value = value
    )
    class Experience(value: Int) : Field<Int>(
        title = "Experience",
        table = "MiscDataDynamic",
        identifiers = arrayOf("DataName" to "ExperiencePoints"),
        valueColumn = "DataValue",
        value = value
    )
    class Galleons(value: Int) : Field<Int>(
        title = "Galleons",
        table = "InventoryDynamic",
        identifiers = arrayOf("CharacterID" to "Player0", "ItemID" to "Knuts"),
        valueColumn = "Count",
        value = value
    )
    class TalentPoints(value: Int) : Field<Int>(
        title = "Talent Points",
        table = "MiscDataDynamic",
        identifiers = arrayOf("DataOwner" to "Player0", "DataName" to "PerkPoints"),
        valueColumn = "DataValue",
        value = value
    )
}