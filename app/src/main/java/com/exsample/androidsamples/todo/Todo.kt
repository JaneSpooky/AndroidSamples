package com.exsample.androidsamples.todo

import java.util.*

class Todo {
    var id: Long = System.currentTimeMillis()
    var name: String = ""
    var dedLineAt: Date? = null
    var completedAt: Date? = null
    var createdAt: Date = Date()
    var updatedAt: Date = Date()
    var deletedAt: Date? = null
}