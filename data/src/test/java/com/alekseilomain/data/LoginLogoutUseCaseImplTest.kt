package com.alekseilomain.data

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import usecase.LoginUseCaseImpl
import usecase.LogoutUseCaseImpl

private val SEED_KEY = stringPreferencesKey("seed")

@OptIn(ExperimentalCoroutinesApi::class)
class LoginLogoutUseCaseTest {

    private lateinit var prefs: FakePreferencesDataStore
    private lateinit var login: LoginUseCaseImpl
    private lateinit var logout: LogoutUseCaseImpl

    @Before
    fun setUp() {
        prefs = FakePreferencesDataStore()
        login  = LoginUseCaseImpl(prefs)
        logout = LogoutUseCaseImpl(prefs)
    }

    @Test
    fun `login writes seed to preferences`() = runTest {
        val seed = "xyz"
        login(seed)
        val stored = prefs.data.first()[SEED_KEY]
        assertEquals("Сохранённый seed должен совпадать", seed, stored)
    }

    @Test
    fun `logout clears seed from preferences`() = runTest {
        // сначала руками наполним «фейковый» стор
        prefs.updateData { current ->
            current.toMutablePreferences().apply {
                this[SEED_KEY] = "willBeRemoved"
            }.toPreferences()
        }
        assertNotNull("Предусловие: seed должен быть сохранён", prefs.data.first()[SEED_KEY])

        logout()
        val after = prefs.data.first()[SEED_KEY]
        assertNull("После логаута seed должен быть null", after)
    }
}