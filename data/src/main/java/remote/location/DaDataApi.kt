package remote.location

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface DaDataApi {
    @POST("suggestions/api/4_1/rs/geolocate/address")
    suspend fun geolocate(
        @Header("Authorization") token: String,
        @Body request: GeolocateRequest
    ): GeolocateResponse
}