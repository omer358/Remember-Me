package com.omo.rememberme.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omo.rememberme.domain.model.People
import com.omo.remindme.database.PeopleDao

@Database(entities = [People::class],version = 1,exportSchema = true)
abstract class PeopleDatabase : RoomDatabase(){
    abstract val peopleDao: PeopleDao
}