package remote.randomuser

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RandomUserResponse(
    val results: List<UserDto>
)

@JsonClass(generateAdapter = true)
data class UserDto(
    val name: NameDto,
    val email: String
)

@JsonClass(generateAdapter = true)
data class NameDto(
    val last: String
)