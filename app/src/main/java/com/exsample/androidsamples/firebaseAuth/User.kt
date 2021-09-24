package com.exsample.androidsamples.firebaseAuth

import java.util.*

class User {
    var name: String = ""
    var uid: String = ""

    var deletedAt: Date? = null

    // 国籍, 得意言語(複数あるかも)

    var ngUids: List<String> = listOf()
}