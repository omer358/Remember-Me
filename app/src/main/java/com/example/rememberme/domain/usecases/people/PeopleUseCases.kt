package com.example.rememberme.domain.usecases.people

data class PeopleUseCases(
    val getAllPeople: GetAllPeople,
    val getPersonById: GetPersonById,
    val insertPerson: InsertNewPerson,
    val updatePerson: UpdatePerson
)