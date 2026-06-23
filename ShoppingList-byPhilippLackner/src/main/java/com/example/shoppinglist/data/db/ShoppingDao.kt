package com.example.shoppinglist.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppinglist.data.db.entities.ShoppingItem

@Dao
interface ShoppingDao {

    //@androidx.room.
    //@Upsert(ShoppingItem::class)
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)    //
    suspend fun upsert(item: ShoppingItem)

    @Delete
    suspend fun delete(item: ShoppingItem)

    @Query("SELECT * from shopping_items")
    fun getAllShoppingItems(): LiveData<List<ShoppingItem>>
}