package com.example.randomquote.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database([com.example.randomquote.models.Result::class], version = 1)
// Here, I have to use fully qualified name for `Result` entity, otherwise room will refer Result as kotlin.Result not com.example.randomquote.models.Result
abstract class QuoteDatabase : RoomDatabase() {

    abstract fun quoteDao(): QuoteDao

    companion object {
        @Volatile
        private var INSTANCE: QuoteDatabase? = null

        fun getDatabase(context: Context): QuoteDatabase {
            synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    QuoteDatabase::class.java,
                    "quoteDB"
                ).build()
            }
            return INSTANCE!!
        }
    }
}