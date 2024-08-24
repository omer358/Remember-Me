import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.omo.rememberme.data.manager.SettingsManagerImpl
import com.omo.rememberme.domain.model.ThemeMode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SettingsManagerImplTest {
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
        // Mock DataStore to return a specific value
        // Assume we have set the ThemeMode to SYSTEM previously
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
