package com.omo.rememberme.domain.usecases.people

import com.omo.rememberme.domain.model.People
import com.omo.rememberme.domain.repository.PeopleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPeople @Inject constructor(
    private val peopleRepository: PeopleRepository
) {
    operator fun invoke(): Flow<List<People>> {
        return peopleRepository.getAllPeople()
    }

}