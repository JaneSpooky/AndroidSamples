package com.exsample.androidsamples.viewPager

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

open class Book(
    @PrimaryKey open var id : String = UUID.randomUUID().toString(),
    @Required open var name : String = "",
    open var price : Long = 0
) : RealmObject() {}