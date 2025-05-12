package com.alekseilomain.domain.usecase

import com.alekseilomain.domain.model.Coordinates

interface GetLocationUseCase {
    /** Получение текущих координат*/
    suspend operator fun invoke(): Coordinates?
}