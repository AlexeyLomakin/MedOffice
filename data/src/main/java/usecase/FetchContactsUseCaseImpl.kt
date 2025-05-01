package usecase

import com.alekseilomain.domain.usecase.FetchContactsUseCase
import com.alekseilomain.domain.repository.ContactsRepository
import javax.inject.Inject

class FetchContactsUseCaseImpl @Inject constructor(
    private val repository: ContactsRepository
) : FetchContactsUseCase {
    override suspend operator fun invoke(seed: String) {
        repository.fetchAndCache(seed)
    }
}