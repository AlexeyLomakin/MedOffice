package com.alekseilomain.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alekseilomain.data.database.model.ContactEntity

@Database(
    entities = [ContactEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}