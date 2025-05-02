package com.alekseilomain.domain.usecase

import com.alekseilomain.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ObserveContactsUseCase {
    /** Загрузка всех контактов из базы*/
    operator fun invoke(): Flow<List<Contact>>
}