package usecase

import com.alekseilomain.domain.model.Contact
import com.alekseilomain.domain.repository.ContactsRepository
import com.alekseilomain.domain.usecase.ObserveContactsUseCase
import kotlinx.coroutines.flow.Flow

class ObserveContactsUseCaseImpl(
    private val repository: ContactsRepository
) : ObserveContactsUseCase {
    override operator fun invoke(): Flow<List<Contact>> =
        repository.observeAll()
}