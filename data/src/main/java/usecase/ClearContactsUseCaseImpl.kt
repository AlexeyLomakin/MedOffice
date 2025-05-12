package usecase

import com.alekseilomain.domain.usecase.ClearContactsUseCase
import com.alekseilomain.domain.repository.ContactsRepository
import javax.inject.Inject

class ClearContactsUseCaseImpl @Inject constructor(
    private val repository: ContactsRepository
) : ClearContactsUseCase {
    override suspend fun invoke() {
        repository.clearAll()
    }
}