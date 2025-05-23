package com.alekseilomain.domain.usecase

interface FetchContactsPageUseCase {
    /** Загружает из сети и кеширует в БД список контактов по seed */
    suspend operator fun invoke(seed: String, page: Int)
}