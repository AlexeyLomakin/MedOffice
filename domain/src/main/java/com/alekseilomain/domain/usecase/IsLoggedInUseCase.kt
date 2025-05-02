package com.alekseilomain.domain.usecase

import kotlinx.coroutines.flow.Flow

interface IsLoggedInUseCase {
    /** Проверка авторизации*/
    operator fun invoke(): Flow<Boolean>
}