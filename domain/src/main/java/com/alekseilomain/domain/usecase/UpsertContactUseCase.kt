package com.alekseilomain.domain.usecase

import com.alekseilomain.domain.model.Contact

interface UpsertContactUseCase {
    /** изменение или добавление контакта*/
    suspend operator fun invoke(contact: Contact)
}