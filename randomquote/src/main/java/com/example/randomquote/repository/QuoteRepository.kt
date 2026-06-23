package com.example.randomquote.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.randomquote.TAG
import com.example.randomquote.api.QuoteService
import com.example.randomquote.db.QuoteDatabase
import com.example.randomquote.models.QuoteList
import com.example.randomquote.utils.NetworkUtils

class QuoteRepository(
    private val quoteService: QuoteService,
    /*private val quoteDao: QuoteDao*/
    private val quoteDatabase: QuoteDatabase,
    private val applicationContext: Context
) {

    /*suspend fun getQuotes(page: Int): retrofit2.Response<QuoteList> {     //This retrofit.Response class and result.isSuccessful is only available for Network call. If want to check if database request is successful or not, we don't have any Room.Response class for that.
        return quoteService.getQuotes(page)
        //return quoteDatabase.quoteDao().getQuotes()
    }*/


    /*private val quotesLiveData = MutableLiveData<QuoteList>()
    val quotes: LiveData<QuoteList> = quotesLiveData

    suspend fun getQuotes(page: Int) {
        if (NetworkUtils.Companion.isInternetAvailable(applicationContext)) {
            try {
                val result = quoteService.getQuotes(page)
                if (result?.body() != null) {
                    //adding the network response to database
                    quoteDatabase.quoteDao().addQuotes(result.body()!!.results)

                    quotesLiveData.postValue(result.body())
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception on background thread: ${e}")
                Log.e(TAG, "Exception on background thread: ${e.stackTrace}", e.cause)
            }
        } else {
            val quotes = quoteDatabase.quoteDao().getQuotes()
            //Because quotesLiveData has <QuoteList> as data type, we need to create a dummy object of QuoteList data class
            val quoteList = QuoteList(1, 1, 1, quotes, 1, 1)
            quotesLiveData.postValue(quoteList)
        }
    }*/


    private val quotesLiveData = MutableLiveData<Response<QuoteList>>()
    val quotes: LiveData<Response<QuoteList>> = quotesLiveData

    //@MainThread   // Call this function on the main thread because room and retrofit internally switches threads to background
    suspend fun getQuotes(page: Int) {
        if (NetworkUtils.Companion.isInternetAvailable(applicationContext)) {
            //Fetch data from API
            try {
                val result = quoteService.getQuotes(page)
                if (result?.body() != null) {
                    //adding the network response to database
                    quoteDatabase.quoteDao().addQuotes(result.body()!!.results)

                    //setting the received value to the LiveData
                    quotesLiveData.postValue(Response.Success(result.body()!!))
                } else {
                    //when result.body() == null
                    quotesLiveData.postValue(Response.Error("API Error"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "getQuotes: Some network error occurred", e.cause)
                quotesLiveData.postValue(Response.Error(e.message.toString()))
            }
        } else {
            //Fetch data from database
            try {
                val quotes = quoteDatabase.quoteDao().getQuotes()
                //Because quotesLiveData has <QuoteList> as data type, we need to create a dummy object of QuoteList data class
                val quoteList = QuoteList(1, 1, 1, quotes, 1, 1)
                quotesLiveData.postValue(Response.Success(quoteList))
            } catch (e: Exception) {
                //Toast.makeText(applicationContext, "Some database error occurred", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "getQuotes: Some database error occurred", e.cause)
                quotesLiveData.postValue(Response.Error<QuoteList>(e.message.toString()))
            }
        }
    }


    suspend fun getQuotesBackground() {
        //val randomNumber = Random.nextInt()
        val randomNumber = (Math.random() * 10).toInt()
        Log.d(TAG, "getQuotesBackground: $randomNumber")
        val result = quoteService.getQuotes(randomNumber)
        if (result.body() != null) {
            quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
        }
    }
}