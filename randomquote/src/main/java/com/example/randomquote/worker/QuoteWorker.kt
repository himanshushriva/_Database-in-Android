package com.example.randomquote.worker

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.randomquote.QuoteApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "QuoteWorker"

class QuoteWorker(
    private val context: Context,
    param: WorkerParameters
) : Worker(context, param) {

    override fun doWork(): Result {
        Log.d(TAG, "doWork: called")

        val repository = (context as QuoteApplication).quoteRepository
        CoroutineScope(Dispatchers.IO).launch {
            repository.getQuotesBackground()
        }
        return ListenableWorker.Result.success()
    }
}