package com.example.shoppinglist.ui.shoppinglist

import android.util.Log
import com.example.shoppinglist.data.db.entities.ShoppingItem

interface AddDialogListener {
    fun onAddButtonClicked(item: ShoppingItem)
}