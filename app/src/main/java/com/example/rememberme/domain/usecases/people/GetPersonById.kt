package com.example.rememberme.domain.usecases.people

import com.example.rememberme.domain.model.People
import com.example.rememberme.domain.repository.PeopleRepository
import kotlinx.coroutines.flow.Flow

class GetPersonById(
    private val peopleRepository: PeopleRepository
) {
    suspend operator fun invoke(id: Long): Flow<People?> {
        return peopleRepository.getPeopleById(id)
    }

}