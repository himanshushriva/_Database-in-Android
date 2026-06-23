package com.example.randomquote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomquote.models.QuoteList
import com.example.randomquote.repository.QuoteRepository
import com.example.randomquote.repository.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            //Log.d(TAG, "Thread name: ${Thread.currentThread().name}")
            quoteRepository.getQuotes(1)
            //error("Error occurred")
            //onCleared()
        }
    }

    //val quotes: LiveData<QuoteList> /*= quoteRepository.quotes*/
        /*get() = quoteRepository.quotes
        get() {
            return quoteRepository.quotes
        }*/

    val quotes: LiveData<Response<QuoteList>> = quoteRepository.quotes

    /*suspend fun getQuotes(page: Int): Response<QuoteList> {
        return quoteRepository.getQuotes(page)
    }*/
}