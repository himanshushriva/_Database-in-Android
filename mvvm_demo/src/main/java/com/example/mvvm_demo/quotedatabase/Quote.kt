package com.example.mvvm_demo.quotedatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("quote")
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String,
    val author: String
)
