package com.example.rememberme.domain.usecases.people

import com.example.rememberme.domain.repository.PeopleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeletePersonById(
    private val peopleRepository: PeopleRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(personId: Long) {
        withContext(dispatcher){
            peopleRepository.deletePersonById(personId)

        }
    }

}