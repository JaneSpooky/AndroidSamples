package com.exsample.androidsamples.viewPager

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class QiitaRealm: RealmObject() {
    @PrimaryKey
    var id: String = ""
    var title: String = ""
    var url : String = ""
    var imageUrl : String = ""

    companion object {
        fun findAll(): List<QiitaRealm> =
            Realm.getDefaultInstance().use { realm ->//useの前のインスタンスが引数として使えるよになる
                realm.where(QiitaRealm::class.java)
                    .sort("id")
                    .findAll()
                    .also {
                        realm.copyFromRealm(it)
                    }
            }

        fun findBy(id: String): QiitaRealm? =
            Realm.getDefaultInstance().use { realm ->
                realm.where(QiitaRealm::class.java)
                    .equalTo(QiitaRealm::id.name,  id)
                    .findFirst()
                    ?.also {
                        realm.copyFromRealm(it)
                    }
            }
    }
}