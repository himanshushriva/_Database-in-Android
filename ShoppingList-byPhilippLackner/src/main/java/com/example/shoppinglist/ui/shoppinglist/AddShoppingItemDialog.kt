package com.example.shoppinglist.ui.shoppinglist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.example.shoppinglist.R
import com.example.shoppinglist.data.db.entities.ShoppingItem

class AddShoppingItemDialog(
    private val context: Context,
    private val addDialogListener: AddDialogListener
) : AppCompatDialog(context)/*, AddDialogListener*/ {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_shopping_item)

        val tvAdd = findViewById<TextView>(R.id.tvAdd)!!
        val tvCancel = findViewById<TextView>(R.id.tvCancel)!!
        val etName = findViewById<EditText>(R.id.etName)!!
        val etAmout = findViewById<EditText>(R.id.etAmount)!!

        tvAdd.setOnClickListener {
            val name = etName.text.toString()
            val amount = etAmout.text.toString()/*.toInt()*/

            if (name.isEmpty() || amount.isEmpty()) {
                Toast.makeText(context, "Please enter all the information", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val item = ShoppingItem(name, amount.toInt())
            addDialogListener.onAddButtonClicked(item)    // calling onAddButtonClicked method, implemented in ShoppingActivity class.
            dismiss()
        }

        tvCancel.setOnClickListener {
            cancel()
        }
    }
}