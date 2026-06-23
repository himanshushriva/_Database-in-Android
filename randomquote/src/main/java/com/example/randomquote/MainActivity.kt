package com.example.randomquote

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.randomquote.api.QuoteService
import com.example.randomquote.api.RetrofitHelper
import com.example.randomquote.repository.Response
import com.example.randomquote.viewmodels.MainViewModel
import com.example.randomquote.viewmodels.MainViewModelFactory

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val repository = (application as QuoteApplication).quoteRepository

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))
            .get(MainViewModel::class.java)

        /*mainViewModel.quotes.observe(this) {
            Log.d(TAG, "onCreate: ${it.results.count()}")
            Toast.makeText(this, it.results.size.toString(), Toast.LENGTH_SHORT).show()
        }*/


        //How can this be implemented using data binding
        mainViewModel.quotes.observe(this) {    //How can this be implemented using data binding
            /*Log.d(TAG, "onCreate: ${it.data?.results.count()}")
            Toast.makeText(this, it.results.size.toString(), Toast.LENGTH_SHORT).show()*/
            /*Log.d(TAG, "onCreate: ${it.data?.results?.count()}")
            Toast.makeText(this, it.data?.results?.size.toString(), Toast.LENGTH_SHORT).show()*/

            when (it) {
                is Response.Loading -> {
                    //show progress bar
                }
                is Response.Success -> {
                    //hide progress bar
                    val stringBuilder = StringBuilder()
                    it.data?.results?.forEach {
                        stringBuilder.append("${it.content} \n")
                    }
                    findViewById<TextView>(R.id.textView).text = stringBuilder
                }
                is Response.Error -> {
                    //hide progress bar
                    Toast.makeText(this, "${it.errorMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }


        /*lifecycleScope.launch*//*(Dispatchers.IO)*//* {

        // Errors and Exceptions are handled in the Repository, so no need to handle them here
            try {
                Log.d(TAG, "${mainViewModel.getQuotes(1).body()}")
            } catch (e: Exception) {
                Log.e(TAG, "Exception on background thread: ${e}")
                Log.e(TAG, "Exception on background thread: ${e.stackTrace}", e.cause)
            }
        }*/
    }
}