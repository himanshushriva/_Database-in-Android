package com.example.mvvm_demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_demo.quotedatabase.Quote

class MainViewModel(val quoteRepository: QuoteRepository) : ViewModel() {

    fun getQuotes(): LiveData<List<Quote>> {
        return quoteRepository.getQuotes()
    }

    suspend fun insertQuote(quote: Quote) {
        quoteRepository.insertQuote(quote)
    }
}