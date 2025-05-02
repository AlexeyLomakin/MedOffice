package usecase

import com.alekseilomain.domain.usecase.FetchContactsPageUseCase
import com.alekseilomain.domain.repository.ContactsRepository
import javax.inject.Inject

class FetchContactsPageUseCaseImpl @Inject constructor(
    private val repo: ContactsRepository
) : FetchContactsPageUseCase {
    override suspend operator fun invoke(seed: String, page: Int) {
        repo.fetchPage(seed, page)
    }
}