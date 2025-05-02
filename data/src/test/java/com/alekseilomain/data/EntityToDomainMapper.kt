package com.alekseilomain.data

import com.alekseilomain.data.database.model.ContactEntity
import mapper.EntityToDomainMapper
import org.junit.Assert.assertEquals
import org.junit.Test

class EntityToDomainMapperTest {
    private val mapper = EntityToDomainMapper()

    @Test
    fun `map ContactEntity to Contact domain model`() {
        val entity = ContactEntity(
            id = 42,
            lastName = "Petrov",
            email = "petrov@example.com",
            isManual = true
        )

        val domain = mapper.map(entity)

        assertEquals(42L, domain.id)
        assertEquals("Petrov", domain.lastName)
        assertEquals("petrov@example.com", domain.email)
        assertEquals(true, domain.isManual)
    }
}