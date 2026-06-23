package com.example.mvvm_demo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvvm_demo.databinding.ActivityMainBinding
import com.example.mvvm_demo.quotedatabase.Quote
import com.example.mvvm_demo.quotedatabase.QuoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val quoteDao = QuoteDatabase.getDatabase(this).quoteDao()
        val quoteRepository = QuoteRepository(quoteDao)
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(quoteRepository)).get(MainViewModel::class.java)

        //binding.quote = mainViewModel.getQuotes().value.toString()
        mainViewModel.getQuotes().observe(this) {
            binding.quote = it.toString()
        }

        binding.btnInsertQuote.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                mainViewModel.insertQuote(Quote(0, "I am freaking genius.", "Unknown"))
            }
        }
    }
}