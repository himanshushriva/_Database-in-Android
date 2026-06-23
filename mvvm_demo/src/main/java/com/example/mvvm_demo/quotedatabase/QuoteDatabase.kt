package com.example.mvvm_demo.quotedatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database([Quote::class], version = 1)
abstract class QuoteDatabase() : RoomDatabase() {

    abstract fun quoteDao(): QuoteDao

    companion object {
        @Volatile
        private var INSTANCE: QuoteDatabase? = null

        fun getDatabase(context: Context): QuoteDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        QuoteDatabase::class.java,
                        "quotedb")
                        .createFromAsset("quotes.db")
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}