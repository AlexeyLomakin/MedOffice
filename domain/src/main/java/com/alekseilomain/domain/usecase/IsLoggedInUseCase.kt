package com.alekseilomain.domain.usecase

import kotlinx.coroutines.flow.Flow

interface IsLoggedInUseCase {
    operator fun invoke(): Flow<Boolean>
}