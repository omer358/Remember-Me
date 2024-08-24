package com.omo.rememberme.domain.usecases.people

import android.util.Log
import com.omo.rememberme.domain.model.People
import com.omo.rememberme.domain.repository.PeopleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdatePerson @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(person: People) {
        withContext(dispatcher) {
            Log.i(TAG, "UpdatePersonUseCase: invoked")
            peopleRepository.updatePeople(person)
        }
    }

    companion object {
        private const val TAG = "UpdatePerson"
    }
}