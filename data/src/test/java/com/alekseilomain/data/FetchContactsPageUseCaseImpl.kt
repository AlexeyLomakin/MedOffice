package com.alekseilomain.data

import com.alekseilomain.domain.repository.ContactsRepository
import com.alekseilomain.domain.usecase.FetchContactsPageUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import usecase.FetchContactsPageUseCaseImpl

class FetchContactsPageUseCaseImplTest {
    private val repo = mock<ContactsRepository>()
    private val useCase: FetchContactsPageUseCase = FetchContactsPageUseCaseImpl(repo)

    @Test
    fun `invoke calls repository fetchPage with correct args`() = runBlocking {
        useCase("seed123", 5)
        verify(repo).fetchPage("seed123", 5)
    }
}