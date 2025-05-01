package com.alekseilomain.domain.usecase

import com.alekseilomain.domain.model.Contact

interface UpsertContactUseCase {
    suspend operator fun invoke(contact: Contact)
}