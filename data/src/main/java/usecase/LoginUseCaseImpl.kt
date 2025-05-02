package usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.alekseilomain.domain.usecase.LoginUseCase
import javax.inject.Inject

private val SEED_KEY = stringPreferencesKey("seed")

class LoginUseCaseImpl @Inject constructor(
    private val prefs: DataStore<Preferences>
) : LoginUseCase {
    override suspend operator fun invoke(seed: String) {
        prefs.edit { it[SEED_KEY] = seed }
    }
}