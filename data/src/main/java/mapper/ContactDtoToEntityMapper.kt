package mapper

import com.alekseilomain.data.database.model.ContactEntity
import remote.randomuser.ContactDto


class ContactDtoToEntityMapper : Mapper<ContactDto, ContactEntity> {
    override fun map(input: ContactDto) = ContactEntity(
        lastName = input.name.last,
        email    = input.email,
        isManual = false
    )
}