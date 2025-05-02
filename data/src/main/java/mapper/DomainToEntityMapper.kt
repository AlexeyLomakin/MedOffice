package mapper

import com.alekseilomain.data.database.model.ContactEntity
import com.alekseilomain.domain.model.Contact

class DomainToEntityMapper : Mapper<Contact, ContactEntity> {
    override fun map(input: Contact): ContactEntity =
        ContactEntity(
            id       = input.id,
            lastName = input.lastName,
            email    = input.email,
            isManual = input.isManual
        )
}