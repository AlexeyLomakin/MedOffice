package usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.alekseilomain.domain.usecase.GetSeedUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSeedUseCaseImpl @Inject constructor(
    private val prefs: DataStore<Preferences>
) : GetSeedUseCase {
    private val SEED_KEY = stringPreferencesKey("seed")
    override fun invoke(): Flow<String> =
        prefs.data.map { it[SEED_KEY].orEmpty() }
}