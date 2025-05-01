package usecase

import com.alekseilomain.domain.usecase.ClearContactsUseCase
import com.alekseilomain.domain.repository.ContactsRepository

class ClearContactsUseCaseImpl(
    private val repository: ContactsRepository
) : ClearContactsUseCase {
    override suspend fun invoke() {
        repository.clearAll()
    }
}