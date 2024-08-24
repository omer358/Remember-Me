package com.omo.rememberme.domain.usecases.people

import com.omo.rememberme.domain.repository.PeopleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeletePersonById @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(personId: Long) {
        withContext(dispatcher){
            peopleRepository.deletePersonById(personId)

        }
    }

}