package com.omo.rememberme.data.manager

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.test.core.app.ApplicationProvider
import com.omo.rememberme.di.AppModule.dataStore
import com.omo.rememberme.domain.manager.OnBoardingManager
import com.omo.rememberme.utils.Constants.APP_ENTRY
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@OptIn(ExperimentalCoroutinesApi::class)
class OnBoardingManagerImplTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var onBoardingManager: OnBoardingManager

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun saveAppEntry_SavesTrue(): Unit = runTest {
        onBoardingManager.saveAppEntry()
        val result = ApplicationProvider.getApplicationContext<Context>().dataStore.data
            .map { it[booleanPreferencesKey(APP_ENTRY)] ?: false }
            .first()
        assertEquals(true, result)
    }

    @Test
    fun readAppEntry_ReturnsFalseIfNotSaved() = runTest {
        val result = onBoardingManager.readAppEntry().first()
        assertEquals(false, result)
    }
}
