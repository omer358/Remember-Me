package com.example.rememberme.domain.usecases.people

import com.example.rememberme.domain.repository.PeopleRepository

class DeletePersonById(
    private val peopleRepository: PeopleRepository
) {
    suspend operator fun invoke(personId: Long) {
        peopleRepository.deletePersonById(personId)
    }

}