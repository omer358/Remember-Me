//package com.example.rememberme.data.repository
//
//import com.example.rememberme.R
//import com.example.rememberme.data.PeopleRepositoryImpl
//import com.example.rememberme.domain.model.People
//import com.example.remindme.database.PeopleDao
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runTest
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Test
//import org.mockito.MockitoAnnotations
//import javax.inject.Inject
//
//@ExperimentalCoroutinesApi
//class PeopleRepositoryImplTest {
//
//    @Inject
//    private lateinit var peopleDao: PeopleDao
//
//    private lateinit var peopleRepository: PeopleRepositoryImpl
//
//    @Before
//    fun setUp() {
//        MockitoAnnotations.initMocks(this::class)
//        peopleRepository = PeopleRepositoryImpl(peopleDao)
//    }
//
//    @Test
//    fun getAllPeopleShouldReturnListOfPeople() = runTest {
//        // Arrange
//        val peopleList = listOf(
//            People(
//                1, "John", secondName = "Doe", place = "", time = "", gender = "",avatar= R.drawable.ic_m3,),
//            People(1, "John", secondName = "Doe", place = "", time = "", gender = "",avatar= R.drawable.ic_m3,)
//        )
//        peopleRepository.insertPeople(peopleList[0])
//        peopleRepository.insertPeople(peopleList[1])
//
//        // Act
//        val result = peopleRepository.getAllPeople()
//
//        // Assert
//        result.collect {
//            assertEquals(peopleList, it)
//        }
//    }
//
////    @Test
////    fun insertPeopleShouldCallInsertPeopleOnDao() = runBlocking {
////        // Arrange
////        val person = People(1, "John", secondName = "Doe", place = "", time = "", gender = "",avatar= R.drawable.ic_m3,)
////
////        // Act
////        peopleRepository.insertPeople(person)
////
////        // Assert
////        Mockito.verify(peopleDao, Mockito.times(1)).insertPeople(person)
////    }
////
////    @Test
////    fun `deletePeople should call deletePeople on dao`() = runBlocking {
////        // Arrange
////        val person = People(1, "John", secondName = "Doe", place = "", time = "", gender = "",avatar= R.drawable.ic_m3,)
////
////        // Act
////        peopleRepository.deletePeople(person)
////
////        // Assert
////        Mockito.verify(peopleDao, Mockito.times(1)).deletePeople(person)
////    }
////
////    @Test
////    fun `updatePeople should call updatePeople on dao`() = runBlocking {
////        // Arrange
////        val person = People(1, "John Doe")
////
////        // Act
////        peopleRepository.updatePeople(person)
////
////        // Assert
////        Mockito.verify(peopleDao, Mockito.times(1)).updatePeople(person)
////    }
////
////    @Test
////    fun `deleteAllPeople should call deleteAllPeople on dao`() = runBlocking {
////        // Act
////        peopleRepository.deleteAllPeople()
////
////        // Assert
////        Mockito.verify(peopleDao, Mockito.times(1)).deleteAllPeople()
////    }
////
////    @Test
////    fun `getPeopleById should return person`() = runBlocking {
////        // Arrange
////        val person = People(1, "John Doe")
////        Mockito.`when`(peopleDao.getPeopleById(1)).thenReturn(person)
////
////        // Act
////        val result = peopleRepository.getPeopleById(1)
////
////        // Assert
////        assertEquals(person, result)
////    }
////
////    @Test
////    fun `getPeopleByName should return list of people`() = runBlocking {
////        // Arrange
////        val peopleList = listOf(People(1, "John Doe"), People(2, "John Smith"))
////        Mockito.`when`(peopleDao.getPeopleByName("John")).thenReturn(peopleList)
////
////        // Act
////        val result = peopleRepository.getPeopleByName("John")
////
////        // Assert
////        assertEquals(peopleList, result)
////    }
//}
