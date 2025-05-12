package remote.location

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class GeolocateRequest(
    val lat: Double,
    val lon: Double
)
