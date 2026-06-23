package com.example.randomquote.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("quote")
data class Result(
    @PrimaryKey(autoGenerate = true) val quoteId: Int,
    val _id: String,    //val id: String
    val author: String,
    val authorSlug: String,
    val content: String,
    val dateAdded: String,
    val dateModified: String,
    val length: Int
    /*val tags: List<String>*/      //We can remove properties which we don't want (in room or retrofit)
                                    // Also, Room cannot store List<String> directly. We will need Type Converters for it.
)