package usecase

import com.alekseilomain.domain.model.Contact
import com.alekseilomain.domain.repository.ContactsRepository
import com.alekseilomain.domain.usecase.UpsertContactUseCase

class UpsertContactUseCaseImpl(
    private val repository: ContactsRepository
) : UpsertContactUseCase {
    override suspend fun invoke(contact: Contact) {
        repository.upsert(contact)
    }
}