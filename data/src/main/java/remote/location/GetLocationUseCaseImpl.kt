package remote.location

import com.alekseilomain.domain.model.Coordinates
import com.alekseilomain.domain.usecase.GetLocationUseCase
import javax.inject.Inject

class GetLocationUseCaseImpl @Inject constructor(
    private val locationClient: LocationClient
) : GetLocationUseCase {
    override suspend operator fun invoke(): Coordinates? {
        return locationClient.getLastLocation()
    }
}