package com.alekseilomain.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetSeedUseCase {
    /** Получаем текущий seed*/
    operator fun invoke(): Flow<String>
}