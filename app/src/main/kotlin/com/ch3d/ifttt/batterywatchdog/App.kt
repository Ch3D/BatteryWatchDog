package com.ch3d.ifttt.batterywatchdog

import android.app.Application

import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.setDefaultConfiguration(RealmConfiguration.Builder(this).build())
    }
}
