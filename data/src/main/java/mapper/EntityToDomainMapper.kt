package mapper

import com.alekseilomain.data.database.model.ContactEntity
import com.alekseilomain.domain.model.Contact

class EntityToDomainMapper : Mapper<ContactEntity, Contact> {
    override fun map(input: ContactEntity): Contact =
        Contact(
            id       = input.id,
            lastName = input.lastName,
            email    = input.email,
            isManual = input.isManual
        )
}