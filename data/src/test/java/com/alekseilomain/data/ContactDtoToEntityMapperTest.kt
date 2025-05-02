package com.alekseilomain.data


import mapper.ContactDtoToEntityMapper
import org.junit.Assert.assertEquals
import org.junit.Test
import remote.randomuser.ContactDto
import remote.randomuser.NameDto

class ContactDtoToEntityMapperTest {
    private val mapper = ContactDtoToEntityMapper()

    @Test
    fun `map ContactDto to ContactEntity`() {
        val dto = ContactDto(
            name = NameDto(last = "Ivanov"),
            email = "ivanov@example.com"
        )

        val entity = mapper.map(dto)

        assertEquals("Ivanov", entity.lastName)
        assertEquals("ivanov@example.com", entity.email)
        assertEquals(false, entity.isManual)
        assertEquals(0L, entity.id)
    }
}