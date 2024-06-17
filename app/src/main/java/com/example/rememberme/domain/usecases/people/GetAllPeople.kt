package com.example.rememberme.domain.usecases.people

import com.example.rememberme.domain.model.People
import com.example.rememberme.domain.repository.PeopleRepository
import kotlinx.coroutines.flow.Flow

class GetAllPeople(
    private val peopleRepository: PeopleRepository
) {
    operator fun invoke(): Flow<List<People>> {
        return peopleRepository.getAllPeople()
    }

}