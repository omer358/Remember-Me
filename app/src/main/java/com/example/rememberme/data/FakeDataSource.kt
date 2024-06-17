package com.example.rememberme.data

import com.example.rememberme.R
import com.example.rememberme.data.database.People

object FakeDataSource {
    fun getPeopleList(): List<People> {
        val firstNames = listOf(
            "John",
            "Jane",
            "Michael",
            "Emily",
            "Robert",
            "Linda",
            "David",
            "Sarah",
            "James",
            "Jessica",
            "William",
            "Amanda",
            "Joseph",
            "Ashley",
            "Charles",
            "Melissa",
            "Thomas",
            "Stephanie",
            "Daniel",
            "Laura",
            "Matthew",
            "Rachel",
            "Andrew",
            "Megan",
            "Joshua",
            "Jennifer",
            "Anthony",
            "Samantha",
            "Mark",
            "Elizabeth"
        )
        val lastNames = listOf(
            "Doe",
            "Smith",
            "Johnson",
            "Davis",
            "Brown",
            "Taylor",
            "Wilson",
            "Moore",
            "Jackson",
            "White",
            "Harris",
            "Martin",
            "Thompson",
            "Garcia",
            "Martinez",
            "Robinson",
            "Clark",
            "Rodriguez",
            "Lewis",
            "Lee",
            "Walker",
            "Hall",
            "Allen",
            "Young",
            "King",
            "Wright",
            "Scott",
            "Torres",
            "Nguyen",
            "Hill"
        )
        val places = listOf(
            "Central Park",
            "Starbucks",
            "Office",
            "Gym",
            "Conference Hall",
            "Library",
            "Mall",
            "Restaurant",
            "Beach",
            "Museum",
            "Cinema",
            "Zoo",
            "Amusement Park",
            "Aquarium",
            "Sports Arena",
            "Theater",
            "Cafe",
            "Bookstore",
            "Airport",
            "Train Station",
            "Bus Stop",
            "Hotel",
            "Hospital",
            "Clinic",
            "School",
            "University",
            "Park",
            "Playground",
            "Concert Hall",
            "Night Club"
        )
        val times = listOf(
            "12:00 PM",
            "9:00 AM",
            "10:00 AM",
            "6:00 PM",
            "3:00 PM",
            "2:00 PM",
            "11:00 AM",
            "1:00 PM",
            "4:00 PM",
            "5:00 PM",
            "7:00 PM",
            "8:00 PM",
            "6:30 PM",
            "9:30 AM",
            "10:30 AM",
            "11:30 AM",
            "12:30 PM",
            "1:30 PM",
            "2:30 PM",
            "3:30 PM",
            "4:30 PM",
            "5:30 PM",
            "6:00 AM",
            "7:00 AM",
            "8:00 AM",
            "8:30 AM",
            "7:30 AM",
            "6:00 AM",
            "9:00 PM",
            "10:00 PM"
        )
        val notes = listOf(
            "Met during lunch break",
            "Had a coffee together",
            "Discussed project details",
            "Met during workout",
            "Attended a seminar",
            "Studied together",
            "Shopped for clothes",
            "Had dinner together",
            "Relaxed on the beach",
            "Visited an exhibition",
            "Watched a movie",
            "Saw animals",
            "Enjoyed rides",
            "Saw marine life",
            "Watched a game",
            "Watched a play",
            "Had a snack",
            "Bought books",
            "Caught a flight",
            "Caught a train",
            "Waited for the bus",
            "Stayed overnight",
            "Had a checkup",
            "Received treatment",
            "Attended classes",
            "Studied together",
            "Walked in the park",
            "Played together",
            "Attended a concert",
            "Danced at the club"
        )

        return List(30) { index ->
            People(
                id = index.toLong(),
                firstName = firstNames[index % firstNames.size],
                secondName = lastNames[index % lastNames.size],
                place = places[index % places.size],
                time = times[index % times.size],
                note = notes[index % notes.size],
                gender = if (index % 2 == 0) "Male" else "Female",
                avatar = if (index % 2 == 0) {
                    getMaleAvatar(index)
                } else {
                    getFemaleAvatar(index)
                }
            )
        }
    }

    private fun getMaleAvatar(index: Int): Int {
        return when (index % 5) {
            0 -> R.drawable.ic_m1
            1 -> R.drawable.ic_m2
            2 -> R.drawable.ic_m3
            3 -> R.drawable.ic_m4
            else -> R.drawable.ic_m5
        }
    }

    private fun getFemaleAvatar(index: Int): Int {
        return when (index % 5) {
            0 -> R.drawable.ic_f1
            1 -> R.drawable.ic_f2
            2 -> R.drawable.ic_f3
            3 -> R.drawable.ic_f4
            else -> R.drawable.ic_f5
        }
    }
}
