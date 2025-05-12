package remote.randomuser

import androidx.datastore.core.IOException
import com.alekseilomain.data.database.ContactDao
import com.alekseilomain.data.database.model.ContactEntity
import com.alekseilomain.domain.model.Contact
import com.alekseilomain.domain.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mapper.Mapper
import remote.randomuser.model.UserDto
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val api: RandomUserApi,
    private val dao: ContactDao,
    private val mapper: Mapper<UserDto, ContactEntity>
) : ContactsRepository {

    override suspend fun fetchPage(seed: String, page: Int) {
        val response = api.getUsers(seed = seed, page = page)
        if (response.isSuccessful) {
            val userDtos = response.body()?.results.orEmpty()
            userDtos
                .map { dto -> mapper.map(dto) }     // ContactEntity
                .forEach { entity ->

                    dao.insert(entity)
                }
        } else {
            throw IOException("RandomUserApi error ${response.code()}: ${response.message()}")
        }
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