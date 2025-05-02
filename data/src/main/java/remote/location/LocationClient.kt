package remote.location

import com.alekseilomain.domain.model.Coordinates

interface LocationClient {
    suspend fun getLastLocation(): Coordinates?
}