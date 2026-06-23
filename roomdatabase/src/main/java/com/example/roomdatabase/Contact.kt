package com.example.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity("contact")
data class Contact(
    @PrimaryKey(/*autoGenerate = */ true)
    val id: Long /*String*/,
    val name: String,
    val phone: String,
    val createdDate: Date?, //added in version 2, no default value is set, so null will be set to previous entries
    val isActive:Int  /*= 1*/       //added in version 2, default value is set in migration sql query, so no null will be set to previous entries
)