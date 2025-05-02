package com.alekseilomain.domain.usecase

interface GetCityUseCase {
    suspend operator fun invoke(lat: Double, lon: Double): String?
}