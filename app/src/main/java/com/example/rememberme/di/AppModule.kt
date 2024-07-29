package com.example.rememberme.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.rememberme.data.PeopleRepositoryImpl
import com.example.rememberme.data.local.PeopleDatabase
import com.example.rememberme.data.manager.SettingsManagerImpl
import com.example.rememberme.domain.manager.SettingsManager
import com.example.rememberme.domain.repository.PeopleRepository
import com.example.rememberme.domain.usecases.add_person.AddPersonUseCases
import com.example.rememberme.domain.usecases.add_person.ValidateGenderSelectionUseCase
import com.example.rememberme.domain.usecases.add_person.ValidateNamesUseCase
import com.example.rememberme.domain.usecases.add_person.ValidatePlaceUseCase
import com.example.rememberme.domain.usecases.add_person.ValidateTimeUseCase
import com.example.rememberme.domain.usecases.people.DeletePersonById
import com.example.rememberme.domain.usecases.people.GetAllPeople
import com.example.rememberme.domain.usecases.people.GetPersonById
import com.example.rememberme.domain.usecases.people.InsertNewPerson
import com.example.rememberme.domain.usecases.people.PeopleUseCases
import com.example.rememberme.domain.usecases.people.UpdatePerson
import com.example.rememberme.domain.usecases.theme.GetThemeMode
import com.example.rememberme.domain.usecases.theme.IsDarkModeEnabled
import com.example.rememberme.domain.usecases.theme.SetThemeMode
import com.example.rememberme.domain.usecases.theme.ThemeUseCases
import com.example.rememberme.utils.Constants.PEOPLE_DATABASE_NAME
import com.example.remindme.database.PeopleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
        peopleRepository: PeopleRepository
    ): PeopleUseCases {
        return PeopleUseCases(
            getAllPeople = GetAllPeople(peopleRepository),
            getPersonById = GetPersonById(peopleRepository),
            insertPerson = InsertNewPerson(peopleRepository),
            updatePerson = UpdatePerson(peopleRepository),
            deletePersonById = DeletePersonById(peopleRepository)
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
}