package com.alekseilomain.domain.usecase

interface GetCityUseCase {
    /** Получение города по координатам */
    suspend operator fun invoke(lat: Double, lon: Double): String?
}