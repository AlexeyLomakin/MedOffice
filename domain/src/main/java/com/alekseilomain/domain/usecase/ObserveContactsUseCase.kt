package com.alekseilomain.domain.usecase

import com.alekseilomain.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ObserveContactsUseCase {
    operator fun invoke(): Flow<List<Contact>>
}