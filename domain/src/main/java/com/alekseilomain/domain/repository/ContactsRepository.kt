package com.alekseilomain.domain.repository

import com.alekseilomain.domain.model.Contact
import kotlinx.coroutines.flow.Flow


interface ContactsRepository {

    suspend fun fetchPage(seed: String, page: Int)

    fun observeAll(): Flow<List<Contact>>

    suspend fun clearAll()

    suspend fun upsert(contact: Contact)
}