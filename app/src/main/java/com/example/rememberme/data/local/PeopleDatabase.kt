package com.example.rememberme.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rememberme.domain.model.People
import com.example.remindme.database.PeopleDao

@Database(entities = [People::class],version = 1,exportSchema = true)
abstract class PeopleDatabase : RoomDatabase(){
    abstract val peopleDao: PeopleDao
}