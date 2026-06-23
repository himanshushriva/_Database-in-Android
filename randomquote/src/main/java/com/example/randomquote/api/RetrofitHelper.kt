package com.example.randomquote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    //SSL certificate of this api is expired, so we need to bypass the certificate validation.
    // when we run this app on WIFI 4 and WIFI 5, it throws `UnknownHostException` and `GaiException`
    // when we run this app on WIFI 6, it throws `SSLHandshakeException` and `CertificateException`
    // but on WIFI 6, after launching the app few times, it runs as expected, and does not throw any Exception
    private val BASE_URL = "https://api.quotable.io/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .client(UnsafeOkHttpClient.getUnsafeOkHttpClient()) //bypassing the certificate validation
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}