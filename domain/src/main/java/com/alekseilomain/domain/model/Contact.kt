package com.alekseilomain.domain.model

data class Contact(
    val id: Long,

    val lastName: String,

    val email: String,

    /**  true — контакт создан вручную, его можно редактировать  */
    val isManual: Boolean
)
