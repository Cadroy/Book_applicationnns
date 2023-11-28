package com.example.book_applicationnns.Models

import org.mongodb.kbson.ObjectId

data class BookDatas(
    val id: ObjectId,
    val Author: String,
    val BookName: String,
    val Pages: String,
    val PageRead: String,
    val DateBookAdded: String,
    val DateModified: String,
    val PublishedDate: String,
    val BookStatus: String
)