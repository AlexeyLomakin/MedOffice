package usecase

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.alekseilomain.domain.usecase.IsLoggedInUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val SEED_KEY = stringPreferencesKey("seed")

class IsLoggedInUseCaseImpl(
    private val prefs: DataStore<Preferences>
) : IsLoggedInUseCase {

    override fun invoke(): Flow<Boolean> =
        prefs.data
            .map { settings ->
                settings[SEED_KEY]?.isNotBlank() == true
            }
}

