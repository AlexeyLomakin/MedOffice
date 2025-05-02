package remote.randomuser

import com.alekseilomain.data.database.ContactDao
import com.alekseilomain.data.database.model.ContactEntity
import com.alekseilomain.domain.model.Contact
import com.alekseilomain.domain.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val api: RandomUserApi,
    private val dao: ContactDao
) : ContactsRepository {

    override suspend fun fetchPage(seed: String, page: Int) {
        val dtos = api.getUsers(seed = seed, page = page).results
        val entities = dtos.map { dto ->
            ContactEntity(
                lastName = dto.name.last,
                email    = dto.email,
                isManual = false
            )
        }
        entities.forEach { dao.insert(it) }
    }

    override fun observeAll(): Flow<List<Contact>> =
        dao.getAllContacts().map { list ->
            list.map { e ->
                Contact(
                    id       = e.id,
                    lastName = e.lastName,
                    email    = e.email,
                    isManual = e.isManual
                )
            }
        }

    override suspend fun clearAll() = dao.clearAll()

    override suspend fun upsert(contact: Contact) {
        dao.insert(
            ContactEntity(
                id       = contact.id,
                lastName = contact.lastName,
                email    = contact.email,
                isManual = contact.isManual
            )
        )
    }
}