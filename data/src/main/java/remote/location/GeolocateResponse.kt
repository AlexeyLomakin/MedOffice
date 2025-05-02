package remote.location

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeolocateResponse(
    val suggestions: List<Suggestion>
)

@JsonClass(generateAdapter = true)
data class Suggestion(
    val value: String,
    val data: SuggestionData
)

@JsonClass(generateAdapter = true)
data class SuggestionData(
    val city: String
)
