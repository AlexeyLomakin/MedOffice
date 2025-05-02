package com.alekseilomain.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alekseilomain.data.database.model.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts ORDER BY isManual DESC, id DESC")
    fun getAllContacts(): Flow<List<ContactEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ContactEntity)

    @Update
    suspend fun update(entity: ContactEntity)

    @Query("DELETE FROM contacts")
    suspend fun clearAll()

    @Query("SELECT * FROM contacts ORDER BY isManual DESC, id DESC")
    fun getAllFlow(): Flow<List<ContactEntity>>
}