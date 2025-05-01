package com.alekseilomain.domain.usecase

interface ClearContactsUseCase {
    /** Очищает данные при Logout*/
    suspend operator fun invoke()
}