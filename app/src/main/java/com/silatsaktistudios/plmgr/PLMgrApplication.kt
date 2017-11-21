package com.silatsaktistudios.plmgr

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by silatjedi on 11/21/17.
 *
 */
class PLMgrApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
                .name("MPPLDB.realm")
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }
}