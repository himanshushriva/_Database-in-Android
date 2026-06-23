package com.example.randomquote.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.randomquote.models.Result

@Dao
interface QuoteDao {

    @Insert
    suspend fun addQuotes(quotes: List<Result>)

    @Query("SELECT * from quote")   //Quote
    suspend fun getQuotes(): List<Result>

}