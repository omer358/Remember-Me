package com.example.rememberme.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class PeopleTest {

    @Test
    fun testPropertyInitialization() {
        val person = People(
            firstName = "John",
            secondName = "Doe",
            place = "Park",
            time = "12:00",
            note = "Meeting notes",
            gender = "Male",
            avatar = 0
        )

        assertEquals("John", person.firstName)
        assertEquals("Doe", person.secondName)
        assertEquals("Park", person.place)
        assertEquals("12:00", person.time)
        assertEquals("Meeting notes", person.note)
        assertEquals("Male", person.gender)
        assertEquals(0, person.avatar)
    }

    @Test
    fun testToStringMethod() {
        val person = People(
            firstName = "John",
            secondName = "Doe",
            place = "Park",
            time = "12:00",
            note = "Meeting notes",
            gender = "Male",
            avatar = 0
        )

        assertEquals("John Doe", person.toString())
    }

    @Test
    fun testEquality() {
        val person1 = People(
            firstName = "John",
            secondName = "Doe",
            place = "Park",
            time = "12:00",
            note = "Meeting notes",
            gender = "Male",
            avatar = 0
        )

        val person2 = People(
            firstName = "John",
            secondName = "Doe",
            place = "Park",
            time = "12:00",
            note = "Meeting notes",
            gender = "Male",
            avatar = 0
        )

        assertEquals(person1, person2)
    }
}