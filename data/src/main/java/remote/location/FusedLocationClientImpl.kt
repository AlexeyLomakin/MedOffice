package remote.location

import android.annotation.SuppressLint
import android.content.Context
import com.alekseilomain.domain.model.Coordinates
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FusedLocationClientImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationClient {

    private val client by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    override suspend fun getLastLocation(): Coordinates? =
        suspendCancellableCoroutine { cont ->
            client.lastLocation
                .addOnSuccessListener { loc ->
                    cont.resume(loc?.let { Coordinates(it.latitude, it.longitude) })
                }
                .addOnFailureListener { e ->
                    cont.resumeWithException(e)
                }
                .addOnCanceledListener {
                    cont.cancel()
                }
        }
}