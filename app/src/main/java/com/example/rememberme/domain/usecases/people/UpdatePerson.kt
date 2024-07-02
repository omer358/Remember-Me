package com.example.rememberme.domain.usecases.people

import android.util.Log
import com.example.rememberme.domain.model.People
import com.example.rememberme.domain.repository.PeopleRepository

class UpdatePerson(
    private val peopleRepository: PeopleRepository
) {
    suspend operator fun invoke(person: People) {
        Log.i(TAG,"UpdatePersonUseCase: invoked")
        peopleRepository.updatePeople(person)
    }
    companion object {
        private const val TAG = "UpdatePerson"
    }
}