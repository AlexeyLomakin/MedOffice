package com.alekseilomain.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val lastName: String,

    val email: String,

    val isManual: Boolean = false
)
