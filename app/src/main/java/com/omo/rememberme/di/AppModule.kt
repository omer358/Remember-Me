package com.omo.rememberme.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.omo.rememberme.data.PeopleRepositoryImpl
import com.omo.rememberme.data.local.PeopleDatabase
import com.omo.rememberme.data.manager.NotificationService
import com.omo.rememberme.data.manager.OnBoardingManagerImpl
import com.omo.rememberme.data.manager.SettingsManagerImpl
import com.omo.rememberme.domain.manager.OnBoardingManager
import com.omo.rememberme.domain.manager.SettingsManager
import com.omo.rememberme.domain.repository.PeopleRepository
import com.omo.rememberme.domain.usecases.add_person.AddPersonUseCases
import com.omo.rememberme.domain.usecases.add_person.ValidateGenderSelectionUseCase
import com.omo.rememberme.domain.usecases.add_person.ValidateNamesUseCase
import com.omo.rememberme.domain.usecases.add_person.ValidatePlaceUseCase
import com.omo.rememberme.domain.usecases.add_person.ValidateTimeUseCase
import com.omo.rememberme.domain.usecases.people.DeletePersonById
import com.omo.rememberme.domain.usecases.people.GetAllPeople
import com.omo.rememberme.domain.usecases.people.GetPersonById
import com.omo.rememberme.domain.usecases.people.InsertNewPerson
import com.omo.rememberme.domain.usecases.people.PeopleUseCases
import com.omo.rememberme.domain.usecases.people.UpdatePerson
import com.omo.rememberme.domain.usecases.reminders.GetSchedule
import com.omo.rememberme.domain.usecases.reminders.ReminderUseCases
import com.omo.rememberme.domain.usecases.reminders.SetSchedule
import com.omo.rememberme.domain.usecases.theme.GetThemeMode
import com.omo.rememberme.domain.usecases.theme.IsDarkModeEnabled
import com.omo.rememberme.domain.usecases.theme.SetThemeMode
import com.omo.rememberme.domain.usecases.theme.ThemeUseCases
import com.omo.rememberme.utils.Constants.ONBOARDING_SETTINGS
import com.omo.rememberme.utils.Constants.PEOPLE_DATABASE_NAME
import com.omo.remindme.database.PeopleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePeopleDatabase(
        application: Application
    ): PeopleDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = PeopleDatabase::class.java,
            name = PEOPLE_DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePeopleDao(
        peopleDatabase: PeopleDatabase
    ) = peopleDatabase.peopleDao

    @Provides
    @Singleton
    fun providePeopleRepository(
        peopleDao: PeopleDao
    ): PeopleRepository {
        return PeopleRepositoryImpl(peopleDao)
    }

    @Provides
    @Singleton
    fun provideSettingsManager(
        @ApplicationContext context: Context
    ): SettingsManager {
        return SettingsManagerImpl(context)
    }

    @Provides
    @Singleton
    fun providePeopleUseCases(
        peopleRepository: PeopleRepository,
        dispatcher: CoroutineDispatcher
    ): PeopleUseCases {
        return PeopleUseCases(
            getAllPeople = GetAllPeople(peopleRepository),
            getPersonById = GetPersonById(peopleRepository),
            insertPerson = InsertNewPerson(peopleRepository),
            updatePerson = UpdatePerson(peopleRepository),
            deletePersonById = DeletePersonById(peopleRepository, dispatcher)
        )
    }

    @Provides
    @Singleton
    fun provideAddPersonUseCases(): AddPersonUseCases{
        return AddPersonUseCases(
            validateNamesUseCase = ValidateNamesUseCase(),
            validatePlaceUseCase = ValidatePlaceUseCase(),
            validateTimeUseCase = ValidateTimeUseCase(),
            validateGenderSelectionUseCase = ValidateGenderSelectionUseCase()
        )
    }

    @Provides
    @Singleton
    fun provideThemeModeUseCases(
        settingsManager: SettingsManager
    ): ThemeUseCases{
        return ThemeUseCases(
            setThemeMode = SetThemeMode(settingsManager),
            getThemeMode = GetThemeMode(settingsManager),
            isDarkModeEnabled = IsDarkModeEnabled(settingsManager)
        )
    }

    @Provides
    @Singleton
    fun provideReminderUseCases(
        settingsManager: SettingsManager
    ): ReminderUseCases{
        return ReminderUseCases(
            getSchedule = GetSchedule(settingsManager),
            setSchedule = SetSchedule(settingsManager)
        )
    }

    @Provides
    @Singleton
    fun provideNotificationService(@ApplicationContext context: Context): NotificationService {
        return NotificationService(context)
    }


     val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = ONBOARDING_SETTINGS)

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideOnBoardingManagerImpl(
        @ApplicationContext context: Context,
        dataStore: DataStore<Preferences>
    ): OnBoardingManager {
        return OnBoardingManagerImpl(context, dataStore)
    }
}