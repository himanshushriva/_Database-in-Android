package com.example.randomquote.repository

import com.example.randomquote.models.QuoteList

// Method 1
/*sealed class Response {
    class Loading : Response()
    class Success(val quoteList: QuoteList) : Response()
    class Error(val errorMessage: String) : Response()
}*/

// Method 2
/*
sealed class Response(val quoteList: QuoteList?, val errorMessage: String?) {
    class Loading : Response(null, null)
    class Success(quoteList: QuoteList) : Response(quoteList, null)
    class Error(errorMessage: String) : Response(null, errorMessage)
}*/

// Method 3
/*sealed class Response(val quoteList: QuoteList? = null, val errorMessage: String? = null) {
    class Loading : Response()
    class Success(quoteList: QuoteList) : Response(quoteList = quoteList)
    class Error(errorMessage: String) : Response(errorMessage = errorMessage)
}*/


// Method 4
// Generic implementation for any Type of data received through network request
sealed class Response<T>(val data: T? = null, val errorMessage: String? = null) {
    class Loading<T> : Response<T>()
    class Success<T>(data: T) : Response<T>(data = data)
    class Error<T>(errorMessage: String) : Response<T>(errorMessage = errorMessage)
}