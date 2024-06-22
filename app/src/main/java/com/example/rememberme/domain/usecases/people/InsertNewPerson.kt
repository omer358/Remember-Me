package com.example.rememberme.domain.usecases.people

import com.example.rememberme.domain.model.People
import com.example.rememberme.domain.repository.PeopleRepository

class InsertNewPerson(
    private val peopleRepository: PeopleRepository
) {
    suspend operator fun invoke(person: People) {
        peopleRepository.insertNewPerson(person)
    }

}