package com.exsample.androidsamples.todo

import java.util.*

class Todo {
    var id: Long = System.currentTimeMillis()
    var name: String = ""
    var deadLineAt: Date? = null
    var completed: Boolean = false
    var createdAt: Date = Date()
    var updatedAt: Date = Date()
    var deletedAt: Date? = null
}