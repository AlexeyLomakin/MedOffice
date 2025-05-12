package com.alekseilomain.domain.usecase

interface LogoutUseCase {
    /**Очищение данных пользователя */
    suspend operator fun invoke()
}