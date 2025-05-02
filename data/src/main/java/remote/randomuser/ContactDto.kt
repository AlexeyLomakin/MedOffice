package remote.randomuser

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContactDto(
    val name: NameDto,
    val email: String
)

@JsonClass(generateAdapter = true)
data class NameDto(val last: String)
