package com.example.randomquote

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.randomquote.api.QuoteService
import com.example.randomquote.api.RetrofitHelper
import com.example.randomquote.db.QuoteDatabase
import com.example.randomquote.repository.QuoteRepository
import com.example.randomquote.worker.QuoteWorker
import java.util.concurrent.TimeUnit

class QuoteApplication : Application() {

    lateinit var quoteRepository: QuoteRepository

    /*init {
        val quoteService = RetrofitHelper.getInstance().create(QuoteService::class.java)
        //val quoteDao = QuoteDatabase.getDatabase(this).quoteDao()
        val database = QuoteDatabase.getDatabase(applicationContext)

        quoteRepository = QuoteRepository(quoteService, database)
    }*/

    override fun onCreate() {
        super.onCreate()
        initialize()
        setupWorker()
    }


    private fun initialize() {
        val quoteService = RetrofitHelper.getInstance().create(QuoteService::class.java)
        //val quoteDao = QuoteDatabase.getDatabase(this).quoteDao()
        val database = QuoteDatabase.getDatabase(applicationContext)

        quoteRepository = QuoteRepository(quoteService, database, applicationContext)
    }


    private fun setupWorker() {
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workerRequest = PeriodicWorkRequest.Builder(
            QuoteWorker::class.java,
            15, TimeUnit.MINUTES
        ).setConstraints(constraint).build()

        WorkManager.getInstance(this).enqueue(workerRequest)
    }

}