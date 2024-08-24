package com.omo.rememberme.domain.manager

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.omo.rememberme.data.manager.SettingsManagerImpl
import com.omo.rememberme.domain.model.ThemeMode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsManagerImplTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var settingsManager: SettingsManagerImpl
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        settingsManager = SettingsManagerImpl(context)
    }

    @Test
    fun testSetThemeMode() = runBlocking {
        settingsManager.setThemeMode(ThemeMode.DARK)

        val themeMode = settingsManager.getThemeMode().first()
        assertEquals(ThemeMode.DARK, themeMode)
    }

    @Test
    fun testGetThemeMode() = runBlocking {
        settingsManager.setThemeMode(ThemeMode.SYSTEM)

        val themeMode = settingsManager.getThemeMode().first()
        assertEquals(ThemeMode.SYSTEM, themeMode)
    }

    @Test
    fun testIsDarkModeEnabled() = runBlocking {
        settingsManager.setThemeMode(ThemeMode.DARK)

        val isDarkModeEnabled = settingsManager.isDarkModeEnabled().first()
        assertTrue(isDarkModeEnabled)
    }
}
