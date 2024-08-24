    package com.omo.rememberme.domain.usecases.people

    import javax.inject.Inject

    data class PeopleUseCases @Inject constructor(
        val getAllPeople: GetAllPeople,
        val getPersonById: GetPersonById,
        val insertPerson: InsertNewPerson,
        val updatePerson: UpdatePerson,
        val deletePersonById: DeletePersonById

    )
