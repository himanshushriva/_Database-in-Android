package com.example.mvvm_demo

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.mvvm_demo.quotedatabase.Quote
import com.example.mvvm_demo.quotedatabase.QuoteDao
import com.example.mvvm_demo.quotedatabase.QuoteDatabase

class QuoteRepository(private val quoteDao: QuoteDao/*, context: Context*/) /*: Any()*/ {

    /*val database: QuoteDao = QuoteDatabase.getDatabase(context).quoteDao()

    fun getQuotes(): LiveData<List<Quote>> {
        return database.getQuotes()
    }*/

    fun getQuotes(): LiveData<List<Quote>> {
        return quoteDao.getQuotes()
    }

    suspend fun insertQuote(quote: Quote) {
        quoteDao.insertQuote(quote)
    }
}
