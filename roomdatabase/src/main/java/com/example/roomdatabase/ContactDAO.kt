package com.example.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactDAO {

    @Insert
    suspend fun insertContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    //getContacts has LiveData as return type so Room automatically run this on background thread
    @Query("SELECT * FROM contact")
    /*suspend */fun getContacts() : LiveData<List<Contact>>

    @Query("SELECT * FROM contact")
    suspend fun getAllContacts() : List<Contact>
}