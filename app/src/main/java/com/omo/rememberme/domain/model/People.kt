package com.omo.rememberme.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people_table")
data class People (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "first_name") var firstName: String,
    @ColumnInfo(name = "second_name") var secondName: String,
    @ColumnInfo(name = "meeting_place") var place: String,
    @ColumnInfo(name = "meeting_time") var time: String,
    @ColumnInfo(name ="note")var note:String? = null,
    @ColumnInfo(name = "registration_time")val registrationTime: Long = System.currentTimeMillis(),
    var gender:String,
    var avatar: Int){
    override fun toString(): String {
        return "$firstName $secondName"
    }
}