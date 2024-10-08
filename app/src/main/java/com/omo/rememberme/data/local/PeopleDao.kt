package com.omo.remindme.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.omo.rememberme.domain.model.People
import kotlinx.coroutines.flow.Flow

@Dao
interface PeopleDao {

    @Insert
    fun insert(people: People)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(people: List<People>)

    @Update
    fun update (people: People)

    @Query("SELECT * FROM people_table WHERE id = :key")
    fun getPerson(key: Long):Flow<People>

    @Query("DELETE FROM people_table")
    fun clear()

    @Query("SELECT * FROM people_table ORDER BY id DESC")
    fun getAllPeople(): Flow<List<People>>

    @Query("SELECT * FROM people_table ORDER BY id DESC")
    fun getAll(): List<People>

    @Query("DELETE FROM people_table WHERE id = :key")
    fun removePerson(key: Long)


}