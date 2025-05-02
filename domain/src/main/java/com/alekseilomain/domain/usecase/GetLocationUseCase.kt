package com.alekseilomain.domain.usecase

import com.alekseilomain.domain.model.Coordinates

interface GetLocationUseCase {
    suspend operator fun invoke(): Coordinates?
}