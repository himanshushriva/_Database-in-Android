package com.example.shoppinglist.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.data.db.entities.ShoppingItem
import com.example.shoppinglist.ui.shoppinglist.ShoppingViewModel

class ShoppingItemAdapter(
    /*private val*/var items: List<ShoppingItem>,
    private val viewModel: ShoppingViewModel
) : RecyclerView.Adapter<ShoppingItemAdapter.ShoppingViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shopping_item, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ShoppingViewHolder,
        position: Int
    ) {
        holder.apply {
            val currentShoppingItem = items[position]
            tvName.text = currentShoppingItem.name
            tvAmount.text = currentShoppingItem.amount.toString()

            ivDelete.setOnClickListener {
                viewModel.delete(currentShoppingItem)
            }

            ivPlus.setOnClickListener {
                currentShoppingItem.amount++
                viewModel.upsert(currentShoppingItem)
            }

            ivMinus.setOnClickListener {
                if (currentShoppingItem.amount > 0) {
                    currentShoppingItem.amount--
                    viewModel.upsert(currentShoppingItem)
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ShoppingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView
        val tvAmount: TextView
        val ivDelete: ImageView
        val ivPlus: ImageView
        val ivMinus: ImageView
        init {
            with(itemView) {
                tvName = findViewById<TextView>(R.id.tvName)
                tvAmount = findViewById<TextView>(R.id.tvAmount)
                ivDelete = findViewById<ImageView>(R.id.ivDelete)
                ivPlus = findViewById<ImageView>(R.id.ivPlus)
                ivMinus = findViewById<ImageView>(R.id.ivMinus)
            }
        }
    }
}