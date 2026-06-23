package com.example.shoppinglist.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("shopping_items")
data class ShoppingItem(
    @ColumnInfo("item_name")
    val name: String,
    @ColumnInfo("item_amount")
    var amount: Int                 // Here, it is a var because we are increasing and decreasing it in the ShoppingItemAdapter
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null            // Declaring primary key here, so we don't have to pass
                                   // the key manually everytime we create an instance of this data class
}