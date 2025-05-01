package com.alekseilomain.domain.usecase

interface LoginUseCase {
    /** Сохраняет seed и помечает пользователя как «залогинен» */
    suspend operator fun invoke(seed: String)
}