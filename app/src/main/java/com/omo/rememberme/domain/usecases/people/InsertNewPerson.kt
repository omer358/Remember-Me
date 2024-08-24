package com.omo.rememberme.domain.usecases.people

import com.omo.rememberme.domain.model.People
import com.omo.rememberme.domain.repository.PeopleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertNewPerson @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(person: People) {
        withContext(dispatcher) {
            peopleRepository.insertNewPerson(person)
        }
    }

}