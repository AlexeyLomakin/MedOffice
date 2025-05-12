package mapper

import com.alekseilomain.data.database.model.ContactEntity
import remote.randomuser.model.UserDto
import javax.inject.Inject

class UserDtoToEntityMapper @Inject constructor() :
    Mapper<UserDto, ContactEntity> {

    override fun map(input: UserDto): ContactEntity =
        ContactEntity(
            lastName = input.name.last,
            email    = input.email,
            isManual = false
        )
}