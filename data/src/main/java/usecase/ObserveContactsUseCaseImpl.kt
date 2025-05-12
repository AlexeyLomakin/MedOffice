package usecase

import com.alekseilomain.domain.model.Contact
import com.alekseilomain.domain.repository.ContactsRepository
import com.alekseilomain.domain.usecase.ObserveContactsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveContactsUseCaseImpl @Inject constructor(
    private val repository: ContactsRepository
) : ObserveContactsUseCase {
    override operator fun invoke(): Flow<List<Contact>> =
        repository.observeAll()
}