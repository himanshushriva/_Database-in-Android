package com.example.shoppinglist.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglist.data.db.entities.ShoppingItem

@Database(
    [ShoppingItem::class],
    version = 1
)
abstract class ShoppingDatabase() : RoomDatabase() {

    abstract fun getShoppingDao(): ShoppingDao

    companion object {
        @Volatile
        private var instance: ShoppingDatabase? = null
        private val LOCK = Any()

        // The way Philipp do this
        // operator function means whenever we create an instance/object of ShoppingDatabase class,
        // this `operator function invoke` will get invoked.
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ShoppingDatabase::class.java, "ShoppingDB.db"
            ).build()


        // The way I do this
        /*fun getDatabase(context: Context): ShoppingDatabase = synchronized(this) {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingDatabase::class.java,
                    "ShoppingDB.db"
                ).build()
            }
            return instance!!
        }*/
    }
}