package remote.location

import com.alekseilomain.domain.usecase.GetCityUseCase
import javax.inject.Inject
import javax.inject.Named

class GetCityUseCaseImpl @Inject constructor(
    private val api: DaDataApi,
    @Named("DADATA_TOKEN") private val token: String
) : GetCityUseCase {
    override suspend operator fun invoke(lat: Double, lon: Double): String? {
        val response = api.geolocate(
            token   = "Token $token",
            request = GeolocateRequest(lat, lon)
        )
        val city = response.suggestions.firstOrNull()?.data?.city
        return city
    }
}