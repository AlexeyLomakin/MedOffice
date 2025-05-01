package usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.alekseilomain.domain.usecase.LogoutUseCase

private val SEED_KEY = stringPreferencesKey("seed")

class LogoutUseCaseImpl(
    private val prefs: DataStore<Preferences>
) : LogoutUseCase {
    override suspend fun invoke() {
        prefs.edit { it.remove(SEED_KEY) }
    }
}