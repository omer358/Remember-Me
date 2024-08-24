package com.omo.rememberme.domain.usecases.people

import com.omo.rememberme.domain.model.People
import com.omo.rememberme.domain.repository.PeopleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPersonById @Inject constructor(
    private val peopleRepository: PeopleRepository
) {
    suspend operator fun invoke(id: Long): Flow<People?> {
        return peopleRepository.getPeopleById(id)
    }

}